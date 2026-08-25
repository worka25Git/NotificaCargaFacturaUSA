package com.telcel.notifica.carga.factura;

public class Factura {

  private String idFactura;
  private String nombre;
  private String factura;
  private String fecha;
  private long montoCompra;
  private int region;
  private String status;
  private String observaciones;
  private String fechaTelcel;
  private int bandera;
  private String correo;
  private String telefono;

  public Factura() {}

  public Factura(
          String nombre,
          String factura,
          String idFactura,
          String fecha,
          long montoCompra,
          int region,
          String observaciones,
          String fechaTelcel) {

    this.nombre = nombre;
    this.factura = factura;
    this.idFactura = idFactura;
    this.fecha = fecha;
    this.montoCompra = montoCompra;
    this.region = region;
    this.observaciones = observaciones;
    this.fechaTelcel = fechaTelcel;
  }

  public Factura(String rowid, String cliente, String fact, String dia, String des, long monto) {
    this.idFactura = rowid;
    this.nombre = cliente;
    this.factura = fact;
    this.fecha = dia;
    this.observaciones = des;
    this.montoCompra = monto;
  }

  public Factura(String factura, int bandera, String idrow) {
    this.factura = factura;
    this.bandera = bandera;
    this.idFactura = idrow;
  }

  public Factura(String nombre, String correo, String telefono) {
    this.nombre = nombre;
    this.correo = correo;
    this.telefono = telefono;
  }


  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getFactura() {
    return factura;
  }

  public void setFactura(String factura) {
    this.factura = factura;
  }

  public String getIdFactura() {
    return idFactura;
  }

  public void setIdFactura(String idFactura) {
    this.idFactura = idFactura;
  }

  public String getFecha() {
    return fecha;
  }

  public void setFecha(String fecha) {
    this.fecha = fecha;
  }

  public String getObservaciones() {
    return observaciones;
  }

  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }

  public long getMontoCompra() {
    return montoCompra;
  }

  public void setMontoCompra(long montoCompra) {
    this.montoCompra = montoCompra;
  }

  public int getBandera() {
    return bandera;
  }

  public void setBandera(int bandera) {
    this.bandera = bandera;
  }

  public String getCorreo() {
    return correo;
  }

  public void setCorreo(String correo) {
    this.correo = correo;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public int getRegion() {
    return region;
  }

  public void setRegion(int region) {
    this.region = region;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getFechaTelcel() {
    return fechaTelcel;
  }

  public void setFechaTelcel(String fechaTelcel) {
    this.fechaTelcel = fechaTelcel;
  }



  

}