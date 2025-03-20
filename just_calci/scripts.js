function appendValue(value) {
    document.getElementById('expression').value += value;
}
function clearExpression() {
    document.getElementById('expression').value = '';
    document.getElementById('result').innerText = '0';
}
function factorial(n) {
    if (n === 0 || n === 1) return 1;
    return n * factorial(n - 1);
}
function calculate() {
    try {
        let expr = document.getElementById('expression').value;
        expr = expr.replace(/sqrt\(([^)]+)\)/g, 'Math.sqrt($1)');
        expr = expr.replace(/ln\(([^)]+)\)/g, 'Math.log($1)');
        expr = expr.replace(/(\d+)!/g, (match, num) => factorial(parseInt(num)));
        expr = expr.replace(/(\d+)\^(\d+)/g, 'Math.pow($1, $2)');
        let result = eval(expr);
        result = result > 100000000 || result < 0.0001 ? result.toExponential(7) : parseFloat(result.toFixed(7));
        document.getElementById('result').innerText = result;
    } catch {
        document.getElementById('result').innerText = 'Error';
    }
}