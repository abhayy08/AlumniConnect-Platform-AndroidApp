package com.abhay.alumniconnect.presentation.components

import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.abhay.alumniconnect.utils.AppUtils
import java.util.regex.Pattern

@Composable
fun LinkifiedText(
    text: String, modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
    maxLines: Int = Int.MAX_VALUE
) {
    val annotatedString = buildLinkifiedText(text)
    var layoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

    Log.d("LinkifiedText", annotatedString.toString())

    Text(
        text = annotatedString,
        style = style,
        onTextLayout = { layoutResult = it },
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures { offset ->
                layoutResult?.let { layout ->
                    val position = layout.getOffsetForPosition(offset)
                    annotatedString.getStringAnnotations("URL", position, position).firstOrNull()
                        ?.let { annotation ->
                            AppUtils.openLink(annotation.item)
                        }
                }
            }
        },
        maxLines = maxLines
    )
}

@Composable
private fun buildLinkifiedText(text: String): AnnotatedString {
    val urlPattern = Pattern.compile(
        "(?:(?:https?://)?(?:www\\.)?|(?:[a-zA-Z]{2,}\\.){1,2})" +  // Match schemes like http, https, www, or subdomains like en.wikipedia
                "[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}" +                         // Match main domain and TLD
                "(?:/[^\\s]*)?",                                           // Optional path
        Pattern.CASE_INSENSITIVE
    )

    return buildAnnotatedString {
        val matcher = urlPattern.matcher(text)
        var lastIndex = 0

        while (matcher.find()) {
            // Add text before the link
            append(text.substring(lastIndex, matcher.start()))

            val rawUrl = matcher.group()
            val fullUrl = when {
                rawUrl.startsWith("http://") || rawUrl.startsWith("https://") ||
                        rawUrl.startsWith("mailto:") || rawUrl.startsWith("tel:") ||
                        rawUrl.startsWith("geo:") -> rawUrl
                else -> "https://$rawUrl"
            }

            // Add the link with styling and annotation
            pushStringAnnotation("URL", fullUrl)
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Medium
                )
            ) {
                append(rawUrl)
            }
            pop()

            lastIndex = matcher.end()
        }

        // Add any remaining text
        if (lastIndex < text.length) {
            append(text.substring(lastIndex))
        }
    }
}
