package com.example.productivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.productivity.ui.theme.ProductivityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProductivityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProductivityLayout(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ProductivityLayout(modifier: Modifier = Modifier) {
    var scoreInput by rememberSaveable { mutableStateOf("") }
    val score = scoreInput.toDoubleOrNull() ?: 0.00
    val total = finalEvaluation(score)
    Scaffold(topBar = {ProductivityTopBar()}) {
        LazyColumn(contentPadding = it) { }
        QuarterCard(modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_small))
            .clip(MaterialTheme.shapes.large))
        finalEvaluation(score)
    }

}

@Composable
fun QuarterCard(modifier: Modifier = Modifier) {
    var scoreInput by rememberSaveable { mutableStateOf("") }
    val score = scoreInput.toDoubleOrNull() ?: 0.00
    val total = finalEvaluation(score)
    var extraGoals by rememberSaveable { mutableStateOf(false) }
    Card(modifier = modifier) {
        Column(
            modifier = modifier
                .padding(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string._1st_quarter),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
            )
            extraGoals(
                extraGoals = extraGoals,
                onRoundUpChanged = { extraGoals = it }
            )
            EditScore(
                value = scoreInput,
                onValueChange = { scoreInput },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_small)),
            )
        }
    }
}

@Composable
fun finalEvaluation(total: Double) {
    Text(
        text = stringResource(R.string.final_evaluation, total),
        style = MaterialTheme.typography.displayMedium
    )
}

@Composable
fun EditScore(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    Text(
        text = stringResource(R.string.score)
    )
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
    )
}

@Composable
fun extraGoals(
    extraGoals: Boolean,
    onRoundUpChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        horizontalAlignment = Alignment.End
    ) {
        Text(text = stringResource(R.string.extra_goals),
            style = MaterialTheme.typography.bodySmall)
        Switch(
            checked = extraGoals,
            onCheckedChange = onRoundUpChanged,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductivityTopBar(){
    CenterAlignedTopAppBar(
        title = {
            Text(
            text = stringResource(R.string.productivity),
                style = MaterialTheme.typography.titleLarge
            )}
    )
}

@Preview(showBackground = true)
@Composable
fun ProductivityLayoutPreview() {
    ProductivityTheme {
        ProductivityLayout()
    }
}