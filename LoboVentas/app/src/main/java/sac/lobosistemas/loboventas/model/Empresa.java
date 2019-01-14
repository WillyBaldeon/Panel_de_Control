package sac.lobosistemas.loboventas.model;

public class Empresa {

    private String empresa_razonsocial;
    private String factura_num;
    private int calc;

    public String getFactura_num() {
        return factura_num;
    }

    public void setFactura_num(String factura_num) {
        this.factura_num = factura_num;
    }

    public int getCalc() {
        return calc;
    }

    public void setCalc(int calc) {
        this.calc = calc;
    }

    public String getEmpresa_razonsocial() {
        return empresa_razonsocial;
    }

    public void setEmpresa_razonsocial(String empresa_razonsocial) {
        this.empresa_razonsocial = empresa_razonsocial;
    }

}
