package presentation.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

internal val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(12.dp),
)

val TxtFieldShapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp)
)



val ButtonShapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(28.dp)
)


val BottomBoxShape = Shapes(
    medium = RoundedCornerShape(
        topStart = 14.dp,
        topEnd = 14.dp,
        bottomEnd = 0.dp,
        bottomStart = 0.dp
    )
)

val InputBoxShape = Shapes(
    medium = RoundedCornerShape(14.dp)
)

val NoShapes = Shapes(
    small = RoundedCornerShape(0.dp)
)
