package org.softrami.service;

import org.softrami.model.Producto;

import java.util.List;

public interface IProductoService {
    public List<Producto> listarProductos();

    public Producto buscarProductoPorId(Integer id);

    public Producto guardarProducto(Producto producto);

    //public void actualizarProducto(Producto producto);

    public void eliminarProductoPorId(Integer id);

}
