package com.calculator.model;

public class CalculationResult {
    private double result;
    private String expression;
    private boolean error;
    private String errorMessage;

    public CalculationResult() {}

    public CalculationResult(double result, String expression) {
        this.result = result;
        this.expression = expression;
        this.error = false;
    }

    public static CalculationResult ofError(String message) {
        CalculationResult r = new CalculationResult();
        r.error = true;
        r.errorMessage = message;
        return r;
    }

    public double getResult() { return result; }
    public void setResult(double result) { this.result = result; }

    public String getExpression() { return expression; }
    public void setExpression(String expression) { this.expression = expression; }

    public boolean isError() { return error; }
    public void setError(boolean error) { this.error = error; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
