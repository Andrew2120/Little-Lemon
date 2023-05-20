package com.example.littlelemon.pages.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.AppDatabase
import com.example.littlelemon.MenuItemEntity
import com.example.littlelemon.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(database: AppDatabase, openProfile: () -> Unit) {
    val menuItemsState by database.menuDao().getAllMeals().observeAsState(initial = emptyList())
    var searchPhrase by remember { mutableStateOf("") }
    var categoriesFilter by remember { mutableStateOf("starters") }

    Column {
        Header(openProfile)
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.displaySmall
            )

            Row {
                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.chicago),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.hero_section_description),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(15.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.hero_img),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            TextField(
                value = searchPhrase,
                onValueChange = { searchPhrase = it },
                placeholder = { Text(stringResource(R.string.enter_search_phrase)) },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
        val categoriesList = mutableListOf<String>()
        menuItemsState.forEach {
            categoriesList.add(it.category)
        }


        LazyRow(contentPadding = PaddingValues(horizontal = 16.dp)) {
            items(categoriesList.distinct()) {
                Card(
                    modifier = Modifier
                        .clickable {
                            categoriesFilter = it
                        }
                        .fillMaxWidth()
                        .padding(8.dp),
                ) {
                    Text(text = it, Modifier.padding(8.dp), color = Color.Black)

                }
            }
        }

        LazyColumn {

            items(menuItemsState.sortedBy { it.title }
                .filter { it.title.contains(searchPhrase, ignoreCase = true) }
                .filter { it.category == categoriesFilter }

            ) {
                CardMenuItem(it)
            }
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CardMenuItem(
    item: MenuItemEntity,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "$ ${item.price.toDouble()}",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )
            }
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {

                GlideImage(
                    model = item.image,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop

                )

            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(openProfile: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
            )

        },
        modifier = Modifier.fillMaxWidth(),
        scrollBehavior = null,
        actions = {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        openProfile()
                    }
                    .size(32.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterVertically)
            )
        }
    )


}


@Preview
@Composable
fun HeaderPreview() {
    Header() {}
}
