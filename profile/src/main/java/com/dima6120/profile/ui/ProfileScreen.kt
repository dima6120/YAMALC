package com.dima6120.profile.ui

import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.dima6120.profile.R
import com.dima6120.profile.di.ProfileComponentHolder
import com.dima6120.profile_api.ProfileRoute
import com.dima6120.ui.Screen
import com.dima6120.ui.composable.ErrorScreen
import com.dima6120.ui.composable.LoadingScreen
import com.dima6120.ui.composable.IconButton
import com.dima6120.ui.composable.TopAppBarTitle
import com.dima6120.ui.models.TextUIModel
import com.dima6120.ui.models.text
import com.dima6120.ui.theme.YAMALCTheme
import com.dima6120.ui.theme.YamalcColors
import com.dima6120.ui.theme.Yamalc
import de.palm.composestateevents.EventEffect

@Composable
fun ProfileScreen(
    id: String,
    lifecycle: Lifecycle,
    route: ProfileRoute,
    navController: NavHostController
) {

    Screen(
        id = id,
        lifecycle = lifecycle,
        route = route,
        componentHolder = ProfileComponentHolder
    ) {
        val viewModel = viewModel<ProfileViewModel>(factory = it.provideProfileViewModelFactory())
        val state = viewModel.state

        when (state) {
            is ProfileState.Authorized ->
                AuthorizedScreen(
                    state,
                    onLogoutButtonClick = viewModel::logout
                )

            ProfileState.Loading -> LoadingScreen()

            is ProfileState.Error ->
                ErrorScreen(
                    errorUIModel = state.error,
                    onButtonClick = viewModel::loadProfile
                )

            is ProfileState.Unauthorized ->
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
    state: ProfileState.Unauthorized,
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
                text = stringResource(id = R.string.not_logged_in_title)
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
private fun AuthorizedScreen(
    state: ProfileState.Authorized,
    onLogoutButtonClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TopAppBarTitle(title = state.userInfo.name)
                },
                actions = {
                    IconButton(
                        icon = R.drawable.ic_logout,
                        onClick = onLogoutButtonClick
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = Yamalc.padding.xl)
                .padding(top = Yamalc.padding.xl),
            verticalArrangement = Arrangement.spacedBy(Yamalc.space.s)
        ) {
            UserInfo(userInfoUIModel = state.userInfo)

            AnimeStatistics(animeStatisticsUIModel = state.animeStatistics)
        }
    }

}

@Composable
private fun UserInfo(
    modifier: Modifier = Modifier,
    userInfoUIModel: UserInfoUIModel
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.spacedBy(Yamalc.space.s)
    ) {
        AsyncImage(
            modifier = Modifier.weight(1f),
            model = userInfoUIModel.picture,
            contentDescription = null
        )

        Column(
            modifier = Modifier.weight(2f),
            verticalArrangement = Arrangement.spacedBy(Yamalc.space.s)
        ) {
            UserInfoLine(
                icon = R.drawable.ic_joined_at,
                text = userInfoUIModel.joinedAt
            )

            userInfoUIModel.birthday?.let {
                UserInfoLine(
                    icon = R.drawable.ic_birthday,
                    text = it
                )
            }

            userInfoUIModel.gender?.let {
                UserInfoLine(
                    icon = it.icon,
                    text = it.name
                )
            }

            userInfoUIModel.location?.let {
                UserInfoLine(
                    icon = R.drawable.ic_location,
                    text = it
                )
            }
        }
    }
}

@Composable
private fun UserInfoLine(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    text: TextUIModel
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Yamalc.space.xs)
    ) {
        Icon(
            modifier = Modifier.align(Alignment.CenterVertically),
            painter = painterResource(id = icon),
            tint = YamalcColors.Gray78,
            contentDescription = ""
        )

        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Normal),
            text = text.text
        )
    }
}

@Composable
private fun AnimeStatistics(
    modifier: Modifier = Modifier,
    animeStatisticsUIModel: AnimeStatisticsUIModel
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(Yamalc.space.s)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            AnimeValue(
                title = stringResource(id = R.string.anime_days),
                value = animeStatisticsUIModel.days.toString()
            )

            AnimeValue(
                title = stringResource(id = R.string.completed),
                value = animeStatisticsUIModel.completedCount.toString()
            )

            AnimeValue(
                title = stringResource(id = R.string.mean_score),
                value = animeStatisticsUIModel.meanScore.toString()
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.anime_statistics_bar_height))
        ) {
            Bar(
                modifier = Modifier.weight(animeStatisticsUIModel.watchingWeight),
                color = YamalcColors.Green
            )

            Bar(
                modifier = Modifier.weight(animeStatisticsUIModel.completedWeight),
                color = YamalcColors.Blue
            )

            Bar(
                modifier = Modifier.weight(animeStatisticsUIModel.onHoldWeight),
                color = YamalcColors.Yellow
            )

            Bar(
                modifier = Modifier.weight(animeStatisticsUIModel.droppedWeight),
                color = YamalcColors.Red
            )

            Bar(
                modifier = Modifier.weight(animeStatisticsUIModel.planToWatchWeight),
                color = YamalcColors.Gray80
            )
        }
    }
}

@Composable
private fun AnimeValue(
    modifier: Modifier = Modifier,
    title: String,
    value: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Yamalc.space.xs)
    ) {
        Text(
            style = MaterialTheme.typography.subtitle1,
            text = title
        )

        Text(
            style = MaterialTheme.typography.h6,
            text = value
        )
    }
}

@Composable
private fun Bar(
    modifier: Modifier = Modifier,
    color: Color
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .background(color)
    )
}

@Preview(widthDp = 250)
@Composable
private fun UserInfoPreview() {
    YAMALCTheme {
        UserInfo(
            userInfoUIModel = UserInfoUIModel(
                name = TextUIModel.clearText("dima6120"),
                picture = "https://upload.wikimedia.org/wikipedia/ru/thumb/6/62/Kermit_the_Frog.jpg/267px-Kermit_the_Frog.jpg",
                gender = GenderUIModel(
                    icon = R.drawable.ic_male,
                    name = TextUIModel.stringResource(R.string.male_gender)
                ),
                birthday = TextUIModel.clearText("Apr 11, 1994"),
                location = TextUIModel.clearText("Russia"),
                joinedAt = TextUIModel.clearText("Jul 18, 2011")
            )
        )
    }
}

@Preview(widthDp = 250)
@Composable
private fun AnimeStatisticsPreview() {
    YAMALCTheme {
        AnimeStatistics(
            animeStatisticsUIModel = AnimeStatisticsUIModel(
                days = 198.4f,
                completedCount = 652,
                meanScore = 7.79f,
                watchingWeight = 0.1f,
                completedWeight = 0.6f,
                onHoldWeight = 0.15f,
                droppedWeight = 0.05f,
                planToWatchWeight = 0.1f
            )
        )
    }
}

@Preview
@Composable
private fun UserInfoLinePreview() {
    YAMALCTheme {
        UserInfoLine(
            icon = R.drawable.ic_joined_at,
            text = TextUIModel.clearText("Jul 18, 2011")
        )
    }
}