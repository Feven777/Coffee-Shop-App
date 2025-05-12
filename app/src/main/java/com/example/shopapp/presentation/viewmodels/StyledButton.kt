import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun StyledButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier // Corrected this to accept a Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(text = text) // Ensure Text is used properly
    }
}
