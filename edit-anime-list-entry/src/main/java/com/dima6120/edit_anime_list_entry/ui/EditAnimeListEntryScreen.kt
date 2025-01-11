package com.dima6120.edit_anime_list_entry.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dima6120.core_api.model.mylist.ListStatusModel
import com.dima6120.edit_anime_list_entry.R
import com.dima6120.edit_anime_list_entry.di.EditAnimeListEntryComponentHolder
import com.dima6120.edit_anime_list_entry_api.EditAnimeListEntryRoute
import com.dima6120.ui.Screen
import com.dima6120.ui.composable.IconButton
import com.dima6120.ui.composable.TextButton
import com.dima6120.ui.models.text
import com.dima6120.ui.theme.YAMALCTheme
import com.dima6120.ui.theme.Yamalc
import com.dima6120.ui.theme.YamalcColors
import com.dima6120.ui.theme.toColor
import de.palm.composestateevents.EventEffect
import de.palm.composestateevents.NavigationEventEffect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import kotlin.math.min

@Composable
fun EditAnimeListEntryScreen(
    id: String,
    lifecycle: Lifecycle,
    route: EditAnimeListEntryRoute,
    navController: NavHostController
) {
    Screen(
        id = id,
        lifecycle = lifecycle,
        route = route,
        componentHolder = EditAnimeListEntryComponentHolder
    ) { component ->
        val viewModel = viewModel<EditAnimeListEntryViewModel>(factory = component.provideEditAnimeListEntryViewModelFactory())
        val state = viewModel.state.value
        val snackbarHostState = remember { SnackbarHostState() }
        val context = LocalContext.current

        NavigationEventEffect(
            event = state.navigateBackEvent,
            onConsumed = viewModel::navigateBackEventConsumed
        ) {
            navController.navigateUp()
        }

        EventEffect(
            event = state.showSnackbarEvent,
            onConsumed = viewModel::showSnackbarEventConsumed
        ) { message ->
            snackbarHostState.showSnackbar(message.text(context))
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                         IconButton(
                             icon = R.drawable.ic_close,
                             onClick = { navController.navigateUp() }
                         )
                    },
                    actions = {
                        if (!state.newEntry) {
                            TextButton(
                                text = stringResource(id = R.string.remove_button),
                                enabled = state.actionButtonsEnabled,
                                elevation = null,
                                onClick = viewModel::remove
                            )
                        }

                        TextButton(
                            text = stringResource(id = R.string.save_button),
                            enabled = state.actionButtonsEnabled,
                            elevation = null,
                            onClick = viewModel::save
                        )
                    }
                )
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = Yamalc.padding.xl)
                    .padding(top = Yamalc.padding.xl),
                verticalArrangement = Arrangement.spacedBy(Yamalc.space.xxl)
            ) {
                Text(
                    text = state.animeInfo.title.text,
                    style = Yamalc.type.fieldValue,
                    color = YamalcColors.Black
                )

                Column (
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(Yamalc.space.m)
                ) {
                    Title(
                        title = stringResource(id = R.string.edit_anime_list_entry_status_title),
                        value = state.animeInfo.status.text
                    )

                    ListStatusCards(
                        selectedListStatus = state.myListStatus.status,
                        availableListStatuses = state.availableListStatuses,
                        onListStatusClick = viewModel::statusSelected
                    )
                }

                Column (
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(Yamalc.space.m)
                ) {
                    Title(
                        title = stringResource(id = R.string.edit_anime_list_entry_progress_title),
                        value = state.animeInfo.episodes.text
                    )

                    val maxEpisodes = state.episodes?.let { it + 1 } ?: Int.MAX_VALUE

                    ValueSelector(
                        valuesCount = maxEpisodes,
                        value = { it.toString() },
                        selectedIndex = min(state.myListStatus.episodesWatched, maxEpisodes),
                        onValueSelected = viewModel::episodesWatchedSelected
                    )
                }

                Column (
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(Yamalc.space.m)
                ) {
                    Title(
                        title = stringResource(id = R.string.edit_anime_list_entry_score_title),
                    )

                    ValueSelector(
                        valuesCount = 11,
                        value = { if (it == 0) "-" else it.toString() },
                        selectedIndex = state.myListStatus.score,
                        onValueSelected = viewModel::scoreSelected
                    )
                }
            }
        }
    }
}

@Composable
private fun ValueSelector(
    modifier: Modifier = Modifier,
    valuesCount: Int,
    value: (Int) -> String,
    selectedIndex: Int,
    onValueSelected: (Int) -> Unit
) {
    val state = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    
    LaunchedEffect(Unit) {
        state.scrollToItem(selectedIndex)

        snapshotFlow { state.layoutInfo }
            .mapNotNull { layoutInfo ->
                val center = (layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset) / 2
                layoutInfo.visibleItemsInfo
                    .find {
                        val itemStart = it.offset - layoutInfo.viewportStartOffset

                        center in itemStart..itemStart + it.size
                    }
                    ?.index
            }
            .distinctUntilChanged()
            .collect(onValueSelected)
    }

    BoxWithConstraints(
        modifier = modifier.fillMaxWidth()
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = maxWidth / 2 - 28.dp),
            verticalAlignment = Alignment.CenterVertically,
            state = state,
            flingBehavior = rememberSnapFlingBehavior(lazyListState = state)
        ) {
            items(count = valuesCount) {
                Box(
                    modifier = Modifier
                        .size(width = 56.dp, height = 48.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                coroutineScope.launch {
                                    state.animateScrollToItem(it)
                                }
                            }
                        )
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = value(it),
                        style = Yamalc.type.fieldValue,
                        color = YamalcColors.Black
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .size(width = 56.dp, height = 48.dp)
                .border(
                    width = 2.dp,
                    shape = RoundedCornerShape(4.dp),
                    color = MaterialTheme.colors.primaryVariant
                )
                .align(Alignment.Center)
        )
    }
}

@Preview(widthDp = 300)
@Composable
private fun ValueSelectorPreview() {
    YAMALCTheme {
        ValueSelector(
            valuesCount = 50,
            value = { it.toString() },
            selectedIndex = 2,
            onValueSelected = {}
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ListStatusCards(
    modifier: Modifier = Modifier,
    selectedListStatus: ListStatusModel,
    availableListStatuses: List<ListStatusUIModel>,
    onListStatusClick: (ListStatusModel) -> Unit
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Yamalc.space.m),
        verticalArrangement = Arrangement.spacedBy(Yamalc.space.m),
    ) {
        availableListStatuses.forEach {
            val borderColor =
                if (it.statusModel == selectedListStatus)
                    Color.Transparent
                else
                    YamalcColors.Gray78

            val textColor =
                if (it.statusModel == selectedListStatus)
                    MaterialTheme.colors.surface
                else
                    YamalcColors.Gray5C


            val backgroundColor =
                if (it.statusModel == selectedListStatus)
                    selectedListStatus.toColor()
                else
                    MaterialTheme.colors.surface

            Card(
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { onListStatusClick(it.statusModel) }
                    ),
                backgroundColor = backgroundColor,
                shape = RoundedCornerShape(4.dp),
                elevation = 0.dp,
            ) {
                Box(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(4.dp),
                            color = borderColor
                        )
                        .padding(
                            horizontal = Yamalc.padding.m,
                            vertical = Yamalc.padding.m
                        )
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = it.name.text,
                        style = Yamalc.type.fieldValue,
                        color = textColor
                    )
                }
            }
        }
    }
}

@Composable
private fun Title(
    modifier: Modifier = Modifier,
    title: String,
    value: String? = null
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = Yamalc.type.fieldTitle,
            color = YamalcColors.Gray78
        )

        value?.let {
            Text(
                text = it,
                style = Yamalc.type.fieldTitle,
                color = YamalcColors.Gray78
            )
        }
    }
}