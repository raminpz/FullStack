package org.softrami.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softrami.exception.RecursoNoEncontradoException;
import org.softrami.model.Producto;
import org.softrami.service.ProductoServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("inventario-app")
@CrossOrigin(value = "http://localhost:4200")
public class ProductoController {

    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);
    private final ProductoServicio productoServicio;

    public ProductoController(ProductoServicio productoServicio) {
        this.productoServicio = productoServicio;
    }

    @GetMapping("/productos")
    public List<Producto> getProductos() {
        List<Producto> productos = productoServicio.listarProductos();
        logger.info("Productos obtenidos:");
        productos.forEach(producto -> logger.info(producto.toString()));
        return productos;
    }

    @PostMapping("/productos")
    public Producto agregarProducto(@RequestBody Producto producto) {
        logger.info("Producto a agregar: " + producto);
        this.productoServicio.guardarProducto(producto);
        return producto;
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable("id") Integer id) {
        Producto producto = this.productoServicio.buscarProductoPorId(id);
        if (producto != null)
            return ResponseEntity.ok(producto);
        else
           throw new RecursoNoEncontradoException("El recurso solicitado no existe" + id);
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable("id") Integer id, @RequestBody Producto producto) {
        Producto productoActualizado = this.productoServicio.buscarProductoPorId(id);

        if (producto == null)
            throw new RecursoNoEncontradoException("El recurso solicitado no existe" + id);

        productoActualizado.setDescripcion(producto.getDescripcion());
        productoActualizado.setPrecio(producto.getPrecio());
        productoActualizado.setExistencia(producto.getExistencia());
        this.productoServicio.guardarProducto(productoActualizado);

        return ResponseEntity.ok(productoActualizado);
    }


    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarProducto(@PathVariable("id") int id) {
        Producto producto = this.productoServicio.buscarProductoPorId(id);

        if (producto == null)
            throw new RecursoNoEncontradoException("El recurso solicitado no existe" + id);

        this.productoServicio.eliminarProductoPorId(producto.getIdProducto());
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }

}
