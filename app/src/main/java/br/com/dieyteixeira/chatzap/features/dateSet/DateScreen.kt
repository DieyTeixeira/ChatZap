package br.com.dieyteixeira.chatzap.features.dateSet

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.dieyteixeira.chatzap.ui.theme.ChatZapTheme
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateScreen(modifier: Modifier = Modifier) {
    Scaffold(
        Modifier
            .fillMaxSize()
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
        ) {
            var openDatePicker by remember {
                mutableStateOf(false)
            }
            var date by remember {
                mutableStateOf("")
            }
            TextField(
                date,
                onValueChange = {},
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                label = {
                    Text(text = "Data")
                },
                interactionSource = remember {
                    MutableInteractionSource()
                }.also {
                    LaunchedEffect(it) {
                        it.interactions.collectLatest { interaction ->
                            if(interaction is PressInteraction.Release) {
                                openDatePicker = true
                            }
                        }
                    }
                },
                readOnly = true
            )
            val state = rememberDatePickerState()
            AnimatedVisibility(openDatePicker) {
                DatePickerDialog(
                    onDismissRequest = {
                        openDatePicker = false
                    },
                    confirmButton = {
                        Button(onClick = {
                            state.selectedDateMillis?.let { millis ->
                                date = Instant
                                    .ofEpochMilli(millis)
                                    .atZone(ZoneId.of("UTC"))
                                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                            }
                            openDatePicker = false
                        }) {
                            Text(text = "Selecionar")
                        }
                    }
                ) {
                    DatePicker(state)
                }
            }
        }
    }
}

@Preview
@Composable
private fun DateScreenPreview() {
    ChatZapTheme {
        DateScreen()
    }
}