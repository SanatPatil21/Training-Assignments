function showTool(toolId) {
    document.getElementById("loan-calculator").style.display = "none";
    document.getElementById("deposit-calculator").style.display = "none";

    //Reset all tool selectors
    let selectors = ["loan-calculator-selector", "deposit-calculator-selector"];
    selectors.forEach(selectorId => {
        let selector = document.getElementById(selectorId);
        selector.style.background = "linear-gradient(135deg, #667eea 0%, #764ba2 100%)";
        selector.style.transform = "scale(1)";
        selector.style.boxShadow = "none";
        selector.style.transition = "all 0.3s ease-in-out"; // Smooth transition
    });

    // Show selected tool & update its selector styling
    let selectedTool = document.getElementById(toolId);
    let selectedSelector = document.getElementById(toolId + "-selector");

    if (selectedTool && selectedSelector) {
        selectedTool.style.display = "block";
        selectedSelector.style.background = "linear-gradient(135deg, #4756c0 0%, #5a3d91 100%)";
        selectedSelector.style.transform = "scale(1.05)";
        selectedSelector.style.boxShadow = "0 6px 10px rgba(0, 0, 0, 0.15)";
    }
}

function updateInterestRate() {
    const loanType = document.getElementById("loan-types").value;
    const interestRateField = document.getElementById("interest-rate");

    const interestRates = {
        home: 9,
        car: 10.5,
        personal: 12
    };

    interestRateField.value = interestRates[loanType] || "";


}

function checkTenure(){
    const tenureField = document.getElementById("tenure");
    const loanType = document.getElementById("loan-types").value;
    const tenure = parseInt(tenureField.value);
    const maxTenure = {
        home: 30,
        car: 7,
        personal: 5
    };

    if(tenure > maxTenure[loanType]){
        alert(`Tenure should be less than ${maxTenure[loanType]} years for ${loanType} loan.`);
        tenureField.value = 0;
        document.getElementById("emi-result").innerHTML = "Please enter valid values.";
        return;
    }
}



function checkAmount(){
    const amountField = document.getElementById("loan-amount");
    const loanType = document.getElementById("loan-types").value;
    const amount = parseInt(amountField.value);
    const result = document.getElementById('emi-result');

    const maxAmount = {
        home: 10000000,
        car: 1500000,
        personal: 500000
    };
    const minAmount = {
        home: 500000,
        car: 100000,
        personal: 10000
    };

    if(amount > maxAmount[loanType]){
        // alert(`Loan amount should be less than Rs. ${maxAmount[loanType]} for ${loanType} loan.`);
        amountField.value = "";
        result.value=0;
        result.innerHTML = "Please enter valid values.";    
        return;

    }
    else if(amount < minAmount[loanType]){
        // alert(`Loan amount should be greater than Rs. ${minAmount[loanType]} for ${loanType} loan.`);
        amountField.value = "";
        result.value=0;
        result.innerHTML = "Please enter valid values.";
        return;
    }

}

function calculateEMI() {
    const loanType = document.getElementById("loan-types").value;
    const loanAmount = parseFloat(document.getElementById("loan-amount").value);
    let interestRate = parseFloat(document.getElementById("interest-rate").value);
    const tenure = parseInt(document.getElementById("tenure").value);

    if (isNaN(loanAmount) || isNaN(interestRate) || isNaN(tenure) || loanAmount <= 0 || tenure <= 0) {
        document.getElementById("emi-result").innerHTML = "Please enter valid values.";
        return;
    }
    
    const monthlyRate = (interestRate / 100) / 12;
    const tenureMonths = tenure * 12;

    // EMI Formula
    const emi = (loanAmount * monthlyRate * Math.pow(1 + monthlyRate, tenureMonths)) /
                (Math.pow(1 + monthlyRate, tenureMonths) - 1);



    document.getElementById("emi-result").innerHTML = `Per Month Installments for ${loanType.toUpperCase()} Loan: Rs. ${emi.toFixed(2)}`;
    document.getElementById("emi-result").style.display = "block";
}


// DEPOSIT CALCULATOR


function checkDepositTenure(){
    const tenureField = document.getElementById("deposit-tenure");
    const tenure = parseInt(tenureField.value);
    const maxTenure = 10;
    if(tenure<0){
        alert(`Tenure should be greater than 0 years for Deposit.`);
        tenureField.value = 0;
    }
    if(tenure > maxTenure){
        alert(`Tenure should be less than ${maxTenure} years for Deposit.`);
        tenureField.value = 0;
    }
}

function calculateMaturity(){
    const depositAmount = parseFloat(document.getElementById("deposit-amount").value);
    const tenure = parseFloat(document.getElementById("deposit-tenure").value);
    //Fixing Value here
    const interestRate = 7;

    

    if (isNaN(depositAmount) || isNaN(interestRate) || depositAmount <= 0) {
        document.getElementById("maturity-result").innerHTML = "Please enter valid values.";
        return;
    }

    const monthlyRate = (interestRate / 100) / 12;

    // Maturity Formula
    const maturityAmount = depositAmount * Math.pow(1 + monthlyRate, tenure);

    document.getElementById("maturity-result").innerHTML = `Maturity Amount: Rs. ${maturityAmount.toFixed(2)}`;
    document.getElementById("maturity-result").style.display = "block";

}

