package com.sameerasw.canvas.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * UI component for Notes Role feature controls.
 * Displays buttons for requesting the Notes role and launching content capture.
 */
@Composable
fun NotesRolePanel(
    visible: Boolean,
    isNotesRoleHeld: Boolean,
    isFloatingWindow: Boolean,
    isLaunchedFromLockScreen: Boolean,
    stylusMode: Boolean,
    onRequestNotesRole: () -> Unit = {},
    onLaunchContentCapture: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    if (!visible) return

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display current status
        Text(
            text = "Notes Role Status",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Status indicators
        Text(
            text = "Role: ${if (isNotesRoleHeld) "Held" else "Not held"}",
            style = MaterialTheme.typography.labelSmall,
            color = if (isNotesRoleHeld) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.error
        )

        if (stylusMode) {
            Text(
                text = "Mode: Stylus",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        if (isLaunchedFromLockScreen) {
            Text(
                text = "Launched from lock screen",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Request Notes Role button
        if (!isNotesRoleHeld) {
            Button(
                onClick = onRequestNotesRole,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Request Notes Role")
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        // Content capture button (only in floating window)
        if (isFloatingWindow && isNotesRoleHeld) {
            Button(
                onClick = onLaunchContentCapture,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Capture Content")
            }

            Text(
                text = "Available in floating window mode",
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else if (!isFloatingWindow && isNotesRoleHeld) {
            Text(
                text = "Content capture available when app is in floating window",
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

