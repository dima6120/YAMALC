package com.dima6120.anime_title.ui

import android.content.Intent
import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.dima6120.anime_title.R
import com.dima6120.anime_title.di.AnimeTitleComponentHolder
import com.dima6120.anime_title.utils.Durations
import com.dima6120.anime_title_api.AnimeTitleRoute
import com.dima6120.core_api.model.anime.AnimeId
import com.dima6120.ui.Screen
import com.dima6120.ui.composable.ErrorScreen
import com.dima6120.ui.composable.LoadingScreen
import com.dima6120.ui.composable.IconButton
import com.dima6120.ui.composable.TopAppBarTitle
import com.dima6120.ui.models.TextUIModel
import com.dima6120.ui.models.text
import com.dima6120.ui.theme.YamalcColors
import com.dima6120.ui.theme.Yamalc
import com.dima6120.ui.theme.toColor
import de.palm.composestateevents.EventEffect
import de.palm.composestateevents.NavigationEventEffect
import kotlin.math.absoluteValue
import kotlin.math.min

@Composable
fun AnimeTitleScreen(
    id: String,
    lifecycle: Lifecycle,
    route: AnimeTitleRoute,
    navController: NavHostController
) {
    Screen(
        id = id,
        lifecycle = lifecycle,
        route = route,
        componentHolder = AnimeTitleComponentHolder
    ) {
        val context = LocalContext.current
        val viewModel = viewModel<AnimeTitleViewModel>(factory = it.provideAnimeTitleViewModelFactory())
        val commonEvents = viewModel.commonEvents
        val state = viewModel.state.value

        EventEffect(
            event = commonEvents.openLinkEvent,
            onConsumed = viewModel::openLinkEventConsumed
        ) { link ->
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        if (state is AnimeTitleState.AnimeDetails) {
                            TopAppBarTitle(
                                title = state.animeDetails.titles.first().name
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            icon = com.dima6120.ui.R.drawable.ic_arrow_back,
                            onClick = navController::navigateUp
                        )
                    },
                    actions = {
                        IconButton(
                            icon = R.drawable.ic_open_in_browser,
                            onClick = viewModel::openAnimeInBrowser
                        )
                    }
                )
            },
            floatingActionButton = {
                if (state is AnimeTitleState.AnimeDetails) {
                    val contentColor =
                        if (state.listStatusModel != null)
                            MaterialTheme.colors.secondary
                        else
                            MaterialTheme.colors.primary

                    val backgroundColor = state.listStatusModel?.toColor() ?: MaterialTheme.colors.secondary

                    val icon =
                        if (state.listStatusModel != null)
                            com.dima6120.ui.R.drawable.ic_edit
                        else
                            com.dima6120.ui.R.drawable.ic_add

                    FloatingActionButton(
                        backgroundColor = backgroundColor,
                        contentColor = contentColor,
                        onClick = viewModel::openEditAnimeListEntryScreen
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = icon),
                            contentDescription = null
                        )
                    }
                }
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .padding()
                    .fillMaxSize()
            ) {
                when (state) {
                    is AnimeTitleState.AnimeDetails -> {
                        NavigationEventEffect(
                            event = state.openEditAnimeListEntryScreenEvent,
                            onConsumed = viewModel::openEditAnimeListEntryScreenEventConsumed
                        ) { route ->
                            navController.navigate(route)
                        }

                        AnimeDetailsScreen(
                            state = state,
                            onAnimeClick = { animeId ->
                                navController.navigate(
                                    AnimeTitleRoute(animeId)
                                )
                            }
                        )
                    }

                    is AnimeTitleState.Error -> ErrorScreen(errorUIModel = state.error)

                    AnimeTitleState.Loading -> LoadingScreen()
                }
            }
        }
    }
}

@Composable
private fun AnimeDetailsScreen(
    state: AnimeTitleState.AnimeDetails,
    onAnimeClick: (AnimeId) -> Unit
) {
    val animeDetails = state.animeDetails

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Yamalc.padding.xl)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(Yamalc.space.xxl)
    ) {
        Row(
            modifier = Modifier
                .padding(top = Yamalc.padding.xl)
                .fillMaxWidth()
        ) {
            Pictures(
                modifier = Modifier
                    .weight(2.5f)
                    .aspectRatio(0.65f),
                pictures = animeDetails.pictures
            )

            StatFields(
                modifier = Modifier.weight(1f),
                animeStats = animeDetails.animeStats
            )
        }

        AnimeInfo(
            titles = animeDetails.titles,
            animeInfo = animeDetails.animeInfo
        )

        RelatedAnime(
            modifier = Modifier.padding(bottom = 50.dp),
            relatedAnimeItems = animeDetails.relatedAnimeItems,
            onAnimeClick = onAnimeClick
        )
    }
}

@Composable
private fun AnimeInfo(
    modifier: Modifier = Modifier,
    titles: List<AnimeTitleUIModel>,
    animeInfo: AnimeInfoUIModel
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Yamalc.space.l)
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = titles.first().name.text,
            style = Yamalc.type.title1,
            color = YamalcColors.Black
        )

        AiringStatus(
            status = animeInfo.status,
            typeAndYear = animeInfo.typeAndYear,
            episodesAndDuration = animeInfo.episodesAndDuration
        )
        
        Genres(genres = animeInfo.genres)

        Synopsys(synopsys = animeInfo.synopsys)
        
        Titles(titles = titles)

        InfoFields(
            source = animeInfo.source,
            season = animeInfo.season,
            studios = animeInfo.studios,
            aired = animeInfo.aired,
            rating = animeInfo.rating
        )
    }
}

@Composable
private fun RelatedAnime(
    modifier: Modifier = Modifier,
    relatedAnimeItems: List<RelatedAnimeItemUIModel>,
    onAnimeClick: (AnimeId) -> Unit
) {
    var showMore by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(animationSpec = tween(Durations.EXPAND_DURATION_IN_MS)),
        verticalArrangement = Arrangement.spacedBy(Yamalc.space.s)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Yamalc.space.l)
        ) {
            val items = remember(showMore) {
                val size = if (showMore) relatedAnimeItems.size else min(2, relatedAnimeItems.size)

                relatedAnimeItems.subList(0, size)
            }

            items.forEach {
                RelatedAnimeItem(
                    relationType = it.relationType,
                    animeItems = it.animeItems,
                    onAnimeClick = onAnimeClick
                )
            }
        }

        if (relatedAnimeItems.size > 2) {
            ExpandArrow(
                expanded = showMore,
                onClick = { showMore = !showMore }
            )
        }
    }
}

@Composable
private fun RelatedAnimeItem(
    modifier: Modifier = Modifier,
    relationType: TextUIModel,
    animeItems: List<AnimeItemUIModel>,
    onAnimeClick: (AnimeId) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Yamalc.space.xl)
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = relationType.text,
            style = Yamalc.type.fieldValue,
            color = YamalcColors.Gray5C
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f),
            verticalArrangement = Arrangement.spacedBy(Yamalc.space.s)
        ) {
            animeItems.forEach {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onAnimeClick(it.id) }
                        )
                    ,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    text = it.title.text,
                    style = Yamalc.type.fieldValue.copy(fontWeight = FontWeight.Medium),
                    color = YamalcColors.PrimaryColorLight
                )
            }
        }
    }
}

@Composable
private fun InfoFields(
    modifier: Modifier = Modifier,
    source: TextUIModel,
    season: TextUIModel,
    studios: TextUIModel,
    aired: TextUIModel,
    rating: TextUIModel
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Yamalc.space.xl)
    ) {

        InfoFieldsRow(
            title1 = R.string.rating_field_title,
            value1 = rating,
            title2 = R.string.source_field_title,
            value2 = source
        )

        InfoFieldsRow(
            title1 = R.string.season_field_title,
            value1 = season,
            title2 = R.string.studios_field_title,
            value2 = studios
        )

        InfoFieldsRow(
            title1 = R.string.aired_field_title,
            value1 = aired
        )
    }
}

@Composable
private fun InfoFieldsRow(
    modifier: Modifier = Modifier,
    @StringRes title1: Int,
    value1: TextUIModel,
    @StringRes title2: Int? = null,
    value2: TextUIModel? = null
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Yamalc.space.xl)
    ) {
        AnimeField(
            modifier = Modifier.weight(1f),
            title = title1,
            value = value1
        )

        if (title2 != null && value2 != null) {
            AnimeField(
                modifier = Modifier.weight(1f),
                title = title2,
                value = value2
            )
        }
    }
}

@Composable
private fun Titles(
    modifier: Modifier = Modifier,
    titles: List<AnimeTitleUIModel>
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Yamalc.space.xl)
    ) {
        titles.forEach {
            AnimeField(
                title = it.language,
                value = it.name
            )
        }
    }
}

@Composable
private fun Synopsys(
    modifier: Modifier = Modifier,
    synopsys: TextUIModel
) {
    var showMore by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(animationSpec = tween(Durations.EXPAND_DURATION_IN_MS))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { showMore = !showMore },
    ) {
        if (showMore) {
            Text(
                text = synopsys.text,
                style = Yamalc.type.body1,
                color = YamalcColors.Black
            )
        } else {
            Text(
                text = synopsys.text,
                style = Yamalc.type.body1,
                color = YamalcColors.Black,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
        }

        ExpandArrow(
            expanded = showMore,
            onClick = { showMore = !showMore }
        )
    }
}

@Composable
private fun ExpandArrow(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onClick: () -> Unit
) {
    val arrowIcon = if (expanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ),
    ) {
        Icon(
            modifier = Modifier.align(Alignment.Center),
            imageVector = ImageVector.vectorResource(id = arrowIcon),
            contentDescription = null
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Genres(
    modifier: Modifier = Modifier,
    genres: List<TextUIModel>
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalArrangement = Arrangement.spacedBy(Yamalc.space.m),
        maxItemsInEachRow = 4
    ) {
        genres.forEach {
            Card(
                shape = RoundedCornerShape(12.dp),
                backgroundColor = YamalcColors.SecondaryColorLight
            ) {
                Box(
                    modifier = Modifier
                        .padding(
                            horizontal = Yamalc.padding.m,
                            vertical = Yamalc.padding.s
                        )
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = it.text,
                        style = Yamalc.type.fieldValue,
                        color = YamalcColors.Black
                    )
                }
            }
        }
    }
}

@Composable
private fun AiringStatus(
    modifier: Modifier = Modifier,
    status: TextUIModel,
    typeAndYear: TextUIModel,
    episodesAndDuration: TextUIModel
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(color = YamalcColors.SecondaryVariantColorLight),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = typeAndYear.text,
            style = Yamalc.type.fieldValue,
            color = YamalcColors.Gray5C
        )

        Text(
            text = status.text,
            style = Yamalc.type.fieldValue,
            color = YamalcColors.Gray5C
        )

        Text(
            text = episodesAndDuration.text,
            style = Yamalc.type.fieldValue,
            color = YamalcColors.Gray5C
        )
    }
}

@Composable
private fun Pictures(
    modifier: Modifier = Modifier,
    pictures: List<String>
) {
    val pagerState = rememberPagerState(pageCount = { pictures.size })

    Box(modifier = modifier) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    width = 1.dp,
                    color = YamalcColors.Gray78
                ),
            state = pagerState,
            pageSpacing = Yamalc.padding.xl,
            contentPadding = PaddingValues(Yamalc.padding.xl)
        ) { page ->
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        val pageOffset =
                            ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    },
                model = pictures[page],
                contentScale = ContentScale.Fit,
                contentDescription = null
            )
        }

        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(Yamalc.padding.m),
            text = stringResource(
                id = R.string.pager_counter,
                pagerState.currentPage + 1,
                pictures.size
            ),
            style = Yamalc.type.fieldTitle,
            color = YamalcColors.Gray78
        )
    }
}


@Composable
private fun StatFields(
    modifier: Modifier = Modifier,
    animeStats: AnimeStatsUIModel
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(Yamalc.space.xl)
    ) {
        ScoreLine(score = animeStats.score)

        AnimeField(
            alignment = Alignment.End,
            title = R.string.rank_field_title,
            value = animeStats.rank
        )

        AnimeField(
            alignment = Alignment.End,
            title = R.string.popularity_field_title,
            value = animeStats.popularity
        )

        AnimeField(
            alignment = Alignment.End,
            title = R.string.members_field_title,
            value = animeStats.members
        )
    }
}

@Composable
private fun ScoreLine(
    modifier: Modifier = Modifier,
    score: TextUIModel
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(Yamalc.space.xs)
    ) {
        Text(
            text = stringResource(id = R.string.score_field_title),
            style = Yamalc.type.fieldTitle,
            color = YamalcColors.Gray5C
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(Yamalc.space.xs),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = com.dima6120.ui.R.drawable.ic_score),
                tint = YamalcColors.Gray5C,
                contentDescription = null
            )

            Text(
                text = score.text,
                style = Yamalc.type.title1,
                color = YamalcColors.Gray5C
            )
        }

    }
}

@Composable
private fun AnimeField(
    modifier: Modifier = Modifier,
    alignment: Alignment.Horizontal = Alignment.Start,
    @StringRes title: Int,
    value: TextUIModel,
) {
    AnimeField(
        modifier = modifier,
        alignment = alignment,
        title = stringResource(id = title),
        value = value.text
    )
}

@Composable
private fun AnimeField(
    modifier: Modifier = Modifier,
    alignment: Alignment.Horizontal = Alignment.Start,
    title: TextUIModel,
    value: TextUIModel,
) {
    AnimeField(
        modifier = modifier,
        alignment = alignment,
        title = title.text,
        value = value.text
    )
}

@Composable
private fun AnimeField(
    modifier: Modifier = Modifier,
    alignment: Alignment.Horizontal = Alignment.Start,
    title: String,
    value: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = alignment,
        verticalArrangement = Arrangement.spacedBy(Yamalc.space.xs)
    ) {
        Text(
            text = title,
            style = Yamalc.type.fieldTitle,
            color = YamalcColors.Gray5C
        )

        Text(
            text = value,
            overflow = TextOverflow.Ellipsis,
            style = Yamalc.type.fieldValue
        )
    }
}