package com.dima6120.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.dima6120.anime_title_api.AnimeTitleRoute
import com.dima6120.core_api.model.anime.AnimeId
import com.dima6120.search.R
import com.dima6120.search.di.SearchComponentHolder
import com.dima6120.search.utils.TestTags
import com.dima6120.search_api.SearchRoute
import com.dima6120.ui.Screen
import com.dima6120.ui.composable.ErrorItem
import com.dima6120.ui.composable.ErrorScreen
import com.dima6120.ui.composable.IconButton
import com.dima6120.ui.composable.ItemList
import com.dima6120.ui.composable.LoadingItem
import com.dima6120.ui.composable.LoadingScreen
import com.dima6120.ui.models.ListItemUIModel
import com.dima6120.ui.models.TextUIModel
import com.dima6120.ui.models.text
import com.dima6120.ui.theme.YAMALCTheme
import com.dima6120.ui.theme.Yamalc
import com.dima6120.ui.theme.YamalcColors
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapNotNull

@Composable
fun SearchScreen(
    id: String,
    lifecycle: Lifecycle,
    route: SearchRoute,
    navController: NavHostController
) {

    Screen(
        id = id,
        lifecycle = lifecycle,
        route = route,
        componentHolder = SearchComponentHolder
    ) { component ->
        val viewModel = viewModel<SearchScreenViewModel>(factory = component.provideSearchScreenViewModelFactory())
        val state = viewModel.state.value

        SearchScreenInternal(
            state = state,
            search = viewModel::search,
            searchWithGivenQuery = viewModel::search,
            clearSearch = viewModel::clearSearch,
            queryChanged = viewModel::queryChanged,
            onAnimeItemClick = { animeId ->
                navController.navigate(
                    AnimeTitleRoute(animeId)
                )
            },
            onLoadNextPage = viewModel::nextPage,
            onRetryLoadPageClick = viewModel::nextPage
        )
    }
}

@Composable
internal fun SearchScreenInternal(
    state: SearchScreenState,
    search: () -> Unit,
    searchWithGivenQuery: (String) -> Unit,
    clearSearch: () -> Unit,
    queryChanged: (String) -> Unit,
    onAnimeItemClick: (AnimeId) -> Unit,
    onLoadNextPage: () -> Unit,
    onRetryLoadPageClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    var searchActive by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(end = Yamalc.padding.l)
                    ) {
                        SearchField(
                            modifier = Modifier
                                .testTag(TestTags.SearchField)
                                .align(Alignment.CenterStart)
                                .onFocusChanged { searchActive = it.isFocused },
                            query = state.query,
                            leadingIcon = {
                                IconButton(
                                    icon = R.drawable.ic_search_leading,
                                    tint = YamalcColors.Gray80,
                                    onClick = {
                                        focusManager.clearFocus()
                                        search()
                                    }
                                )
                            },
                            trailingIcon = {
                                IconButton(
                                    icon = R.drawable.ic_search_trailing,
                                    tint = YamalcColors.Gray80,
                                    onClick = {
                                        focusManager.clearFocus()
                                        clearSearch()
                                    }
                                )
                            },
                            onQueryChanged = queryChanged,
                            onSearch = {
                                focusManager.clearFocus()
                                search()
                            }
                        )
                    }
                }
            )
        }
    ) {
        if (searchActive) {
            ResentSearches(
                modifier = Modifier
                    .testTag(TestTags.ResentSearches)
                    .padding(it)
                    .imePadding(),
                queries = state.queries,
                onQueryClick = { query ->
                    focusManager.clearFocus()
                    searchWithGivenQuery(query)
                }
            )
        } else {
            when (state.searchResults) {
                SearchResults.EmptyQuery ->
                    TextScreen(text = stringResource(id = R.string.empty_query))

                SearchResults.EmptyResults ->
                    TextScreen(text = stringResource(id = R.string.no_results))

                is SearchResults.Error ->
                    ErrorScreen(
                        errorUIModel = state.searchResults.error,
                        onButtonClick = search
                    )

                SearchResults.Loading -> LoadingScreen()

                is SearchResults.Results ->
                    AnimeItemList(
                        items = state.searchResults.items,
                        onAnimeItemClick = onAnimeItemClick,
                        onLoadNextPage = onLoadNextPage,
                        onRetryLoadPageClick = onRetryLoadPageClick
                    )
            }
        }
    }
}

@Composable
private fun TextScreen(
    modifier: Modifier = Modifier,
    text: String
) {
    Box(modifier = modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = text,
            style = Yamalc.type.fieldValue,
            color = YamalcColors.Gray5C
        )
    }
}

@Composable
private fun AnimeItemList(
    modifier: Modifier = Modifier,
    items: List<ListItemUIModel<AnimeItemUIModel>>,
    onAnimeItemClick: (AnimeId) -> Unit,
    onLoadNextPage: () -> Unit,
    onRetryLoadPageClick: () -> Unit
) {
    val state = rememberLazyListState()

    LaunchedEffect(state) {
        snapshotFlow { state.layoutInfo.visibleItemsInfo }
            .mapNotNull { it.lastOrNull()?.index }
            .distinctUntilChanged()
            .collect { lastVisibleIndex ->
                val totalItems = state.layoutInfo.totalItemsCount

                if (lastVisibleIndex + 3 > totalItems - 1) {
                    onLoadNextPage()
                }
            }
    }

    ItemList(
        modifier = modifier.fillMaxSize(),
        items = items,
        state = state
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {
            when (it) {
                is ListItemUIModel.Error ->
                    ErrorItem(
                        error = it.error,
                        onButtonClick = onRetryLoadPageClick
                    )

                is ListItemUIModel.Item ->
                    AnimeItem(
                        modifier = Modifier
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = { onAnimeItemClick(it.item.animeId) }
                            ),
                        item = it.item
                    )

                ListItemUIModel.Loading -> LoadingItem()
            }
        }
    }
}

@Composable
private fun AnimeItem(
    modifier: Modifier = Modifier,
    item: AnimeItemUIModel
) {
    Column(modifier = modifier.fillMaxSize()) {
        Row(modifier = modifier.weight(1f)) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(0.7f),
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = item.picture,
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )

                Score(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = Yamalc.padding.m),
                    score = item.score
                )
            }

            AnimeInfo(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                item = item
            )
        }

        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 0.5.dp,
            color = YamalcColors.Gray78
        )
    }
}

@Composable
private fun AnimeInfo(
    modifier: Modifier = Modifier,
    item: AnimeItemUIModel
) {
    Box(
        modifier = modifier
            .padding(
                horizontal = Yamalc.padding.l,
                vertical = Yamalc.padding.m
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart),
            verticalArrangement = Arrangement.spacedBy(Yamalc.space.m)
        ) {
            Text(
                text = item.title.text,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = Yamalc.type.title2,
                color = YamalcColors.Black
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Yamalc.space.m),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimeType(type = item.type)

                Text(
                    modifier = Modifier.weight(1f),
                    text = "${item.episodes.text}, ${item.season.text}",
                    color = YamalcColors.Black
                )
            }
        }

        Members(
            modifier = Modifier.align(Alignment.BottomStart),
            members = item.members
        )
    }
}

@Composable
private fun Members(
    modifier: Modifier = Modifier,
    members: TextUIModel
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Yamalc.space.s),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = members.text,
            style = Yamalc.type.fieldValue,
            color = YamalcColors.Gray78
        )

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_members),
            tint = YamalcColors.Gray78,
            contentDescription = null
        )
    }
}

@Preview
@Composable
private fun MembersPreview() {
    YAMALCTheme {
        Members(members = TextUIModel.clearText("660"))
    }
}

@Composable
private fun AnimeType(
    modifier: Modifier = Modifier,
    type: TextUIModel
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .background(
                shape = RoundedCornerShape(2.dp),
                color = YamalcColors.Red
            )
            .padding(
                vertical = Yamalc.padding.xs,
                horizontal = Yamalc.padding.s
            )
    ) {
        Text(
            text = type.text,
            color = MaterialTheme.colors.secondary,
            style = Yamalc.type.fieldValue
        )
    }
}

@Preview
@Composable
private fun AnimeTypePreview() {
    YAMALCTheme {
        AnimeType(type = TextUIModel.clearText("TV"))
    }
}

@Composable
private fun Score(
    modifier: Modifier = Modifier,
    score: TextUIModel
) {
    Row(
        modifier = modifier
            .wrapContentSize()
            .background(shape = RectangleShape, color = YamalcColors.Gray5C)
            .padding(Yamalc.padding.s),
        horizontalArrangement = Arrangement.spacedBy(Yamalc.space.xs),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = score.text,
            color = MaterialTheme.colors.secondary,
            style = Yamalc.type.fieldValue
        )

        Icon(
            modifier = Modifier.size(16.dp),
            imageVector = ImageVector.vectorResource(id = com.dima6120.ui.R.drawable.ic_score),
            tint = MaterialTheme.colors.secondary,
            contentDescription = null
        )
    }
}

@Preview
@Composable
private fun ScorePreview() {
    YAMALCTheme {
        Score(score = TextUIModel.clearText("8.6"))
    }
}

@Composable
private fun ResentSearches(
    modifier: Modifier = Modifier,
    queries: List<String>,
    onQueryClick: (String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = Yamalc.padding.xl),
                text = stringResource(id = R.string.recent_searches),
                style = Yamalc.type.title2,
                color = YamalcColors.Black
            )

            Divider(
                modifier = Modifier.align(Alignment.BottomStart),
                color = YamalcColors.Gray78
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(
                    horizontal = Yamalc.padding.xl,
                    vertical = Yamalc.space.m
                ),
            verticalArrangement = Arrangement.spacedBy(Yamalc.space.m)
        ) {
            items(queries) {query ->
               QueryItem(
                   modifier = Modifier
                       .clickable(
                           interactionSource = remember { MutableInteractionSource() },
                           indication = null,
                           onClick = { onQueryClick(query) }
                       ),
                   query = query
               )
            }
        }
    }
}

@Composable
private fun QueryItem(
    modifier: Modifier = Modifier,
    query: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(32.dp),
        horizontalArrangement = Arrangement.spacedBy(Yamalc.space.m),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_query_history_item),
            tint = YamalcColors.Gray78,
            contentDescription = null
        )

        Text(
            modifier = Modifier.weight(1f),
            overflow = TextOverflow.Ellipsis,
            text = query,
            style = Yamalc.type.fieldValue
        )
    }
}

@Composable
private fun SearchField(
    modifier: Modifier = Modifier,
    query: String,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    onQueryChanged: (String) -> Unit,
    onSearch: () -> Unit
) {
    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .height(32.dp),
        cursorBrush = SolidColor(MaterialTheme.colors.primary),
        textStyle = Yamalc.type.fieldValue,
        singleLine = true,
        value = query,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(onSearch = { onSearch() }),
        onValueChange = onQueryChanged,
        decorationBox = { inputTextField ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colors.surface
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIcon != null) {
                    leadingIcon()
                }

                Box(Modifier.weight(1f)) {
                    inputTextField()
                }

                if (trailingIcon != null) {
                    trailingIcon()
                }
            }
        }
    )
}