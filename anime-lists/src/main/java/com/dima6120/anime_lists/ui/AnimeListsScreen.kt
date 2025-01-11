package com.dima6120.anime_lists.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.dima6120.anime_lists.R
import com.dima6120.anime_lists.di.AnimeListsComponentHolder
import com.dima6120.anime_lists_api.AnimeListsRoute
import com.dima6120.anime_title_api.AnimeTitleRoute
import com.dima6120.core_api.model.anime.AnimeId
import com.dima6120.core_api.model.mylist.ListStatusModel
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
import com.dima6120.ui.theme.Yamalc
import com.dima6120.ui.theme.YamalcColors
import com.dima6120.ui.theme.toColor
import de.palm.composestateevents.EventEffect
import de.palm.composestateevents.NavigationEventEffect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch

@Composable
fun AnimeListsScreen(
    id: String,
    lifecycle: Lifecycle,
    route: AnimeListsRoute,
    navController: NavHostController
) {

    Screen(
        id = id,
        lifecycle = lifecycle,
        route = route,
        componentHolder = AnimeListsComponentHolder
    ) {
        val viewModel = viewModel<AnimeListsViewModel>(factory = it.providerAnimeListsViewModelFactory())
        val state = viewModel.state.value

        when (state) {
            is AnimeListsState.Authorized -> {
                NavigationEventEffect(
                    event = state.openEditAnimeListEntryScreenEvent,
                    onConsumed = viewModel::openEditAnimeListEntryScreenEventConsumed
                ) { route ->
                    navController.navigate(route)
                }

                AnimeListPages(
                    state = state,
                    onActiveListChanged = viewModel::activeAnimeListChanged,
                    onAnimeItemClick = { animeId ->
                        navController.navigate(
                            AnimeTitleRoute(animeId)
                        )
                    },
                    onEditItemClick = viewModel::openEditAnimeListEntryScreen,
                    onLoadNextPage = viewModel::nextActiveAnimeListPage,
                    onRetryLoadPageClick = viewModel::retry,
                )
            }

            is AnimeListsState.Error ->
                ErrorScreen(
                    errorUIModel = state.error,
                    onButtonClick = viewModel::retry
                )

            AnimeListsState.Loading -> LoadingScreen()

            is AnimeListsState.Unauthorized ->
                UnauthorizedScreen(
                    state = state,
                    onLoginButtonClick = viewModel::login,
                    onOpenLinkEventConsumed = viewModel::openLinkEventConsumed
                )
        }
    }
}

@Composable
private fun UnauthorizedScreen(
    state: AnimeListsState.Unauthorized,
    onLoginButtonClick: () -> Unit,
    onOpenLinkEventConsumed: () -> Unit
) {
    val context = LocalContext.current

    EventEffect(
        event = state.openLinkEvent,
        onConsumed = onOpenLinkEventConsumed
    ) { link ->
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(Yamalc.space.s)
        ) {
            Text(
                style = MaterialTheme.typography.h6,
                text = stringResource(id = R.string.anime_lists_not_logged_in_title)
            )

            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = onLoginButtonClick
            ) {
                Text(
                    text = stringResource(id = com.dima6120.ui.R.string.login_button)
                )
            }
        }

    }
}

@Composable
private fun AnimeListPages(
    modifier: Modifier = Modifier,
    state: AnimeListsState.Authorized,
    onActiveListChanged: (Int) -> Unit,
    onAnimeItemClick: (AnimeId) -> Unit,
    onEditItemClick: (AnimeId) -> Unit,
    onLoadNextPage: () -> Unit,
    onRetryLoadPageClick: () -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = state.activeAnimeListIndex,
        pageCount = { state.animeLists.size }
    )

    val coroutineScope = rememberCoroutineScope()
    
    LaunchedEffect(key1 = pagerState) {
        snapshotFlow {
            pagerState.currentPage
        }.collect(onActiveListChanged)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    ScrollableTabRow(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(end = Yamalc.padding.l),
                        edgePadding = 0.dp,
                        selectedTabIndex = pagerState.currentPage
                    ) {
                        state.animeLists.forEachIndexed { index, animeListUIModel ->
                            Tab(
                                selected = (index == pagerState.currentPage),
                                onClick = {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .fillMaxHeight()
                                        .padding(horizontal = Yamalc.padding.l)
                                ) {
                                    Text(
                                        modifier = Modifier.align(Alignment.Center),
                                        text = animeListUIModel.name.text,
                                        style = Yamalc.type.title2
                                    )
                                }
                            }
                        }
                    }
                }
            )
        }
    ) {
        HorizontalPager(
            modifier = modifier
                .padding(it)
                .fillMaxSize()
            ,
            state = pagerState
        ) { index ->
            AnimeListPage(
                animeListUIModel = state.animeLists[index],
                onAnimeItemClick = onAnimeItemClick,
                onLoadNextPage = onLoadNextPage,
                onRetryLoadPageClick = onRetryLoadPageClick,
                onEditItemClick = onEditItemClick
            )
        }
    }
}

@Composable
private fun AnimeListPage(
    modifier: Modifier = Modifier,
    animeListUIModel: AnimeListUIModel,
    onAnimeItemClick: (AnimeId) -> Unit,
    onEditItemClick: (AnimeId) -> Unit,
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
        items = animeListUIModel.entries,
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
                    AnimeListEntry(
                        modifier = Modifier
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = { onAnimeItemClick(it.item.animeId) }
                            ),
                        entry = it.item,
                        status = animeListUIModel.status,
                        onEditClick = { onEditItemClick(it.item.animeId) }
                    )

                ListItemUIModel.Loading -> LoadingItem()
            }
        }
    }
}

@Composable
private fun AnimeListEntry(
    modifier: Modifier = Modifier,
    entry: AnimeListEntryUIModel,
    status: ListStatusModel,
    onEditClick: () -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        Row(modifier = modifier.weight(1f)) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(0.7f),
                model = entry.picture,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )

            AnimeInfo(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                item = entry,
                status = status
            )

            Actions(
                onEditClick = onEditClick
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
private fun Actions(
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(
                vertical = Yamalc.padding.m
            )
            .padding(end = Yamalc.padding.l)
            .fillMaxHeight()
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.Center)
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(2.dp),
                    color = YamalcColors.Gray5C
                )
                .padding(Yamalc.padding.s)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onEditClick
                )
            ,
            imageVector = ImageVector.vectorResource(id = com.dima6120.ui.R.drawable.ic_edit),
            tint = YamalcColors.Gray5C,
            contentDescription = null
        )
    }
}

@Composable
private fun AnimeInfo(
    modifier: Modifier = Modifier,
    item: AnimeListEntryUIModel,
    status: ListStatusModel
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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(
                        id = R.string.anime_list_entry_type_season,
                        item.type.text, item.season.text
                    ),
                    style = Yamalc.type.fieldValue,
                    color = YamalcColors.Gray78
                )

                Text(
                    text = item.status.text,
                    style = Yamalc.type.fieldValue,
                    color = YamalcColors.Gray78
                )
            }
        }

        EntryProgress(
            modifier = Modifier.align(Alignment.BottomStart),
            status = status,
            progress = item.progress,
            episodes = item.episodes,
            episodesWatched = item.episodesWatched
        )
    }
}

@Composable
private fun EntryProgress(
    modifier: Modifier = Modifier,
    status: ListStatusModel,
    progress: Float,
    episodes: TextUIModel,
    episodesWatched: TextUIModel
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Yamalc.space.m)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .background(YamalcColors.Gray78)
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth(fraction = progress)
                    .fillMaxHeight()
                    .background(status.toColor())
            )
        }

        Text(
            modifier = Modifier.align(Alignment.End),
            text = stringResource(
                id = R.string.anime_list_entry_progress,
                episodesWatched.text, episodes.text
            ),
            style = Yamalc.type.fieldValue,
            color = YamalcColors.Black
        )
    }
}