package com.example.littlelemon.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.example.littlelemon.R

@Composable
fun WarningDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            AlertDialog(
                onDismissRequest = onDismiss,
                title = {
                    Text(text = stringResource(R.string.registration_unsuccessful_please_enter_all_data))
                },
                confirmButton = {
                    Button(onClick = onDismiss) {
                        Text(text = stringResource(id = R.string.ok))
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewWarningDialog() {
    WarningDialog(showDialog = true, onDismiss = {})
}