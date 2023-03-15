package com.example.tipcalculator

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TipTimeScreen()
                }
            }
        }
    }
}

@Composable
fun TipTimeScreen() {
    var tipInput by remember{ mutableStateOf("")  }
    val tipPercent = tipInput.toDoubleOrNull() ?:0.0
    var amountInput by remember {mutableStateOf("0")}
    val amount = amountInput.toDoubleOrNull()?: 0.0
    val tip = calculateTip(amount, tipPercent)

    Column(modifier = Modifier.padding(32.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = stringResource(id =R.string.calculate_tip),
        fontSize = 24.sp,
        modifier = Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.height(16.dp))

        EditNumberField(value= amountInput, onValueChange = {amountInput = it},
            label = R.string.bill_amount,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

        EditNumberField(label = R.string.how_was_the_service, value = tipInput,
            onValueChange ={tipInput = it},
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = stringResource(id = R.string.tip_amount,tip),
        modifier = Modifier.align(Alignment.CenterHorizontally),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
        )
    }

}

@Composable
fun EditNumberField(
    @StringRes label : Int,
    value: String,
    keyboardOptions: KeyboardOptions
    onValueChange: (String)-> Unit,
    modifier: Modifier= Modifier
){

    TextField(value = value,
        onValueChange =onValueChange,
    label = {Text(stringResource(id = label))},
    modifier = Modifier.fillMaxWidth(),
    singleLine = true,
    keyboardOptions = keyboardOptions
    )
}

private fun calculateTip(amount: Double, tipPercent: Double) : String{
    val tip = tipPercent/100 * amount
    return NumberFormat.getCurrencyInstance().format(tip)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipCalculatorTheme {
        TipTimeScreen()
    }
}