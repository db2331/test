1.Scientific Calculator - Apex class

public class ScientificCalculator {
   
    public Decimal num1 { get; set; }
    public Decimal num2 { get; set; }
    public Decimal result { get; set; }
    public String errorMessage { get; set; }

    // Constructor
    public ScientificCalculator() {
        num1 = 0;
        num2 = 0;
        result = null;
        errorMessage = '';
    }

    public void add() { reset(); result = num1 + num2; }
    public void subtract() { reset(); result = num1 - num2; }
    public void multiply() { reset(); result = num1 * num2; }

    public void divide() {
        reset();
        if (num2 == 0) { errorMessage = 'Cannot divide by zero!'; }
        else { result = num1 / num2; }
    }

    public void power() { reset(); result = Math.pow(Double.valueOf(num1), Double.valueOf(num2)); }

    public void sqrt() {
        reset();
        if (num1 < 0) errorMessage = 'Cannot find square root of a negative number!';
        else result = Math.sqrt(Double.valueOf(num1));
    }

    public void log() {
        reset();
        if (num1 <= 0) errorMessage = 'Log undefined for zero or negative numbers!';
        else result = Math.log10(Double.valueOf(num1));
    }

    public void ln() {
        reset();
        if (num1 <= 0) errorMessage = 'Natural log undefined for zero or negative numbers!';
        else result = Math.log(Double.valueOf(num1));
    }

    public void abs() { reset(); result = Math.abs(num1); }
    public void exp() { reset(); result = Math.exp(Double.valueOf(num1)); }
    public void tenPower() { reset(); result = Math.pow(10, Double.valueOf(num1)); }
    public void floor() { reset(); result = Math.floor(Double.valueOf(num1)); }
    public void ceil() { reset(); result = Math.ceil(Double.valueOf(num1)); }
    public void round() { reset(); result = Math.round(Double.valueOf(num1)); }

    public void sin() { reset(); result = Math.sin(num1 * Math.PI / 180); }
    public void cos() { reset(); result = Math.cos(num1 * Math.PI / 180); }
    public void tan() { reset(); result = Math.tan(num1 * Math.PI / 180); }

    public void cot() {
        reset();
        if (Math.tan(num1 * Math.PI / 180) == 0) errorMessage = 'Cotangent undefined!';
        else result = 1 / Math.tan(num1 * Math.PI / 180);
    }

    public void sec() {
        reset();
        if (Math.cos(num1 * Math.PI / 180) == 0) errorMessage = 'Secant undefined!';
        else result = 1 / Math.cos(num1 * Math.PI / 180);
    }

    public void csc() {
        reset();
        if (Math.sin(num1 * Math.PI / 180) == 0) errorMessage = 'Cosecant undefined!';
        else result = 1 / Math.sin(num1 * Math.PI / 180);
    }

    public void factorial() {
        reset();
        if (num1 < 0 || num1 != Math.floor(num1)) errorMessage = 'Factorial is only for non-negative integers!';
        else result = factorial(Integer.valueOf(num1));
    }

    public static Integer factorial(Integer n) {
        if (n == 0 || n == 1) return 1;
        return n * factorial(n - 1);
    }

    private void reset() {
        result = null;
        errorMessage = '';
    }
}

1. Scientific Calculator - VisualForce Page - 

<apex:page controller="ScientificCalculator">
    <apex:form >
        <apex:outputPanel style="max-width: 500px; margin: auto; text-align: center;">
            <apex:pageBlock title="Scientific Calculator">
                <apex:pageBlockSection columns="1">
                    <apex:outputLabel value="Enter Number 1:"/>
                    <apex:inputText value="{!num1}" styleClass="input-box"/>

                    <apex:outputLabel value="Enter Number 2 (if required):"/>
                    <apex:inputText value="{!num2}" styleClass="input-box"/>

                    <!-- Buttons in Rows -->
                    <apex:outputPanel layout="block" style="display: flex; flex-wrap: wrap; justify-content: center;">
                        <apex:commandButton value="+" action="{!add}" styleClass="calc-button"/>
                        <apex:commandButton value="-" action="{!subtract}" styleClass="calc-button"/>
                        <apex:commandButton value="×" action="{!multiply}" styleClass="calc-button"/>
                        <apex:commandButton value="÷" action="{!divide}" styleClass="calc-button"/>
                        <apex:commandButton value="x^y" action="{!power}" styleClass="calc-button"/>
                    </apex:outputPanel>

                    <apex:outputPanel layout="block" style="display: flex; flex-wrap: wrap; justify-content: center;">
                        <apex:commandButton value="√x" action="{!sqrt}" styleClass="calc-button"/>
                        <apex:commandButton value="log" action="{!log}" styleClass="calc-button"/>
                        <apex:commandButton value="ln" action="{!ln}" styleClass="calc-button"/>
                        <apex:commandButton value="|x|" action="{!abs}" styleClass="calc-button"/>
                        <apex:commandButton value="e^x" action="{!exp}" styleClass="calc-button"/>
                    </apex:outputPanel>

                    <apex:outputPanel layout="block" style="display: flex; flex-wrap: wrap; justify-content: center;">
                        <apex:commandButton value="10^x" action="{!tenPower}" styleClass="calc-button"/>
                        <apex:commandButton value="x!" action="{!factorial}" styleClass="calc-button"/>
                        <apex:commandButton value="sin" action="{!sin}" styleClass="calc-button"/>
                        <apex:commandButton value="cos" action="{!cos}" styleClass="calc-button"/>
                        <apex:commandButton value="tan" action="{!tan}" styleClass="calc-button"/>
                    </apex:outputPanel>

                    <apex:outputPanel layout="block" style="display: flex; flex-wrap: wrap; justify-content: center;">
                        <apex:commandButton value="cot" action="{!cot}" styleClass="calc-button"/>
                        <apex:commandButton value="sec" action="{!sec}" styleClass="calc-button"/>
                        <apex:commandButton value="csc" action="{!csc}" styleClass="calc-button"/>
                        <apex:commandButton value="floor" action="{!floor}" styleClass="calc-button"/>
                        <apex:commandButton value="ceil" action="{!ceil}" styleClass="calc-button"/>
                    </apex:outputPanel>

                    <apex:outputPanel layout="block" style="display: flex; flex-wrap: wrap; justify-content: center;">
                        <apex:commandButton value="round" action="{!round}" styleClass="calc-button"/>
                    </apex:outputPanel>

                    <!-- Error Message -->
                    <apex:outputPanel rendered="{!errorMessage != ''}">
                        <p class="error-message">{!errorMessage}</p>
                    </apex:outputPanel>

                    <!-- Result Display -->
                    <apex:outputPanel rendered="{!result != null}">
                        <p class="result-text">Result: <strong>{!result}</strong></p>
                    </apex:outputPanel>

                </apex:pageBlockSection>
            </apex:pageBlock>
        </apex:outputPanel>
    </apex:form>

    <style>
        .calc-button {
            width: 90px;
            height: 40px;
            margin: 5px;
            font-size: 16px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .calc-button:hover {
            background-color: #45a049;
        }
        .input-box {
            width: 90%;
            padding: 5px;
            font-size: 16px;
        }
        .error-message {
            color: red;
            font-weight: bold;
        }
        .result-text {
            font-size: 18px;
            font-weight: bold;
            color: blue;
        }
    </style>
</apex:page>

2. CompareThreeController - class

public class CompareThreeController {
    
    public Double num1 { get; set; }
    public Double num2 { get; set; }
    public Double num3 { get; set; }
    public String result { get; set; }
    
    public CompareThreeController() {
        result = null;
    }
    
    public void compare() {
        if (num1 == null || num2 == null || num3 == null) {
            result = 'Please enter all three numbers.';
            return;
        }
        
        Double maxValue = Math.max(Math.max(num1, num2), num3);
        
        if (maxValue == num1) {
            result = 'Number 1 is greatest: ' + num1;
        } else if (maxValue == num2) {
            result = 'Number 2 is greatest: ' + num2;
        } else {
            result = 'Number 3 is greatest: ' + num3;
        }
    }
}


2. CompareThreeController - vfp

<apex:page controller="CompareThreeController">
    <apex:form >
        <apex:pageBlock title="Find Greatest Among Three Numbers">
            
            <apex:pageBlockSection title="Input Numbers" columns="1">
                <apex:inputText value="{!num1}" label="Enter Number 1" />
                <apex:inputText value="{!num2}" label="Enter Number 2" />
                <apex:inputText value="{!num3}" label="Enter Number 3" />
            </apex:pageBlockSection>

            <apex:pageBlockSection>
                <apex:commandButton value="Compare" action="{!compare}" rerender="resultBlock"/>
            </apex:pageBlockSection>

            <apex:outputPanel id="resultBlock">
                <apex:pageMessages />
                <apex:outputText value="{!result}" rendered="{!NOT(ISNULL(result))}" style="font-weight: bold; font-size: 16px; color: green;" />
            </apex:outputPanel>

        </apex:pageBlock>
    </apex:form>
</apex:page>


3. TemperatureController - class
public class TemperatureController {

    public Double celsius { get; set; }
    public Double fahrenheit { get; set; }
    public String message { get; set; }

    public void convert() {
        if (celsius == null) {
            message = 'Please enter a Celsius value.';
            fahrenheit = null;
        } else {
            fahrenheit = (celsius * 9 / 5) + 32;
            message = 'Converted Fahrenheit value: ' + fahrenheit;
        }
    }
}


3. TemperatureController - vfp
<apex:page controller="TemperatureController">
    <apex:form >
        <apex:pageBlock title="Celsius to Fahrenheit Converter">

            <apex:pageBlockSection title="Input" columns="1">
                <apex:inputText value="{!celsius}" label="Enter Temperature in Celsius"/>
            </apex:pageBlockSection>

            <apex:pageBlockSection>
                <apex:commandButton value="Convert" action="{!convert}" rerender="resultPanel"/>
            </apex:pageBlockSection>

            <apex:outputPanel id="resultPanel">
                <apex:outputText value="{!message}" rendered="{!NOT(ISNULL(message))}"
                                 style="font-weight:bold; font-size:16px; color:blue;"/>
            </apex:outputPanel>

        </apex:pageBlock>
    </apex:form>
</apex:page>

4. ElectricityBillController - class
public class ElectricityBillController {

    public Integer units { get; set; }
    public Double billAmount { get; set; }
    public String message { get; set; }

    public void calculateBill() {
        if (units == null || units < 0) {
            message = 'Please enter a valid (non-negative) number of units.';
            billAmount = null;
            return;
        }

        Double amount = 0.0;
        Integer remaining = units;

        if (remaining > 300) {
            amount += (remaining - 300) * 6;
            remaining = 300;
        }
        if (remaining > 200) {
            amount += (remaining - 200) * 4;
            remaining = 200;
        }
        if (remaining > 100) {
            amount += (remaining - 100) * 2.5;
            remaining = 100;
        }
        amount += remaining * 1.5;

        billAmount = amount;
        message = 'Total bill for ' + units + ' units is ₹' + billAmount.format();
    }
}


4. ElectricityBillCalculator - vfp
<apex:page controller="ElectricityBillController">
    <apex:form >
        <apex:pageBlock title="Electricity Bill Calculator">

            <apex:pageBlockSection columns="1">
                <apex:inputText value="{!units}" label="Enter Units Consumed"/>
                <apex:commandButton value="Calculate" action="{!calculateBill}" rerender="resultPanel"/>
            </apex:pageBlockSection>

            <apex:outputPanel id="resultPanel">
                <apex:outputText value="{!message}" rendered="{!NOT(ISNULL(message))}"
                                 style="font-weight:bold; font-size:16px; color:green;" />
            </apex:outputPanel>

        </apex:pageBlock>
    </apex:form>
</apex:page>

5. CurrencyConverterController - class

public class CurrencyConverterController {

    public Double amount { get; set; }
    public String fromCurrency { get; set; }
    public String toCurrency { get; set; }
    public Double convertedAmount { get; set; }
    public String message { get; set; }

    public Map<String, Double> exchangeRates { get; set; }

    public CurrencyConverterController() {   
    exchangeRates = new Map<String, Double>{
    'USD' => 1.0,
    'INR' => 85.97,
    'EUR' => 94.26,
    'GBP' => 111.23,
    'CNY' => 11.92
    };
    }

    public List<SelectOption> getCurrencyOptions() {
        List<SelectOption> options = new List<SelectOption>();
        for (String code : exchangeRates.keySet()) {
            options.add(new SelectOption(code, code));
        }
        return options;
    }

    public void convertCurrency() {
        if (amount == null || fromCurrency == null || toCurrency == null) {
            message = 'Please enter amount and select currencies.';
            convertedAmount = null;
            return;
        }

        Double fromRate = exchangeRates.get(fromCurrency);
        Double toRate = exchangeRates.get(toCurrency);
        convertedAmount = (amount / fromRate) * toRate;
        message = amount + ' ' + fromCurrency + ' = ' + convertedAmount.format() + ' ' + toCurrency;
    }
}

5. CurrencyConverterPage.vfp - vfp
<apex:page controller="CurrencyConverterController">
    <apex:form >
        <apex:pageBlock title="Currency Converter">

            <apex:pageBlockSection columns="1">
                <apex:inputText value="{!amount}" label="Amount" />

                <apex:selectList value="{!fromCurrency}" size="1" label="From Currency">
                    <apex:selectOptions value="{!currencyOptions}" />
                </apex:selectList>

                <apex:selectList value="{!toCurrency}" size="1" label="To Currency">
                    <apex:selectOptions value="{!currencyOptions}" />
                </apex:selectList>

                <apex:commandButton value="Convert" action="{!convertCurrency}" rerender="resultPanel" />
            </apex:pageBlockSection>

            <apex:outputPanel id="resultPanel">
                <apex:outputText value="{!message}" rendered="{!NOT(ISNULL(message))}" 
                                 style="font-weight:bold; font-size:16px; color:blue;" />
            </apex:outputPanel>

        </apex:pageBlock>
    </apex:form>
</apex:page>

