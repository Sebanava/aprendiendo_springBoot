package com.usuario.aprendiendo.inico.de.sesion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.Spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class UsuarioController {
    
    private List<String> productos = new ArrayList<>(Arrays.asList("manzana", "pera", "limon", "uva", "sandia", "naranja"));
    private List<String> usuariosRegistrados = new ArrayList<>();
    private List<String> contraseñasRegistradas = new ArrayList<>();
    private List<String> Carrito = new ArrayList<>();

    @GetMapping("/crear_usuario")
    public String mostrarCrearUsuario() {
        return "crear_usuario";
    }

    @PostMapping("/crear_usuario")
    public String crear_usuario (
        @RequestParam String Usuario,
        @RequestParam String contraseña,
        Model model){

            String mensaje = "";
            boolean usuariocreado = false;

            if (usuariosRegistrados.contains(Usuario)){
            mensaje = "Nombre de usuairo ya registrado";
            usuariocreado = false;

            }else{
                usuariosRegistrados.add(Usuario);
                contraseñasRegistradas.add(contraseña);

                mensaje = "Usuario registrado";
                usuariocreado = true;
            }

        model.addAttribute("mensaje", mensaje);
        model.addAttribute("usuariocreado", usuariocreado);
        model.addAttribute("mostrarResultado", true);

            return "crear_usuario";
        }

    @GetMapping("/inicio_sesion")
    public String mostrarInicioSesion() {
    return "inicio_sesion";
    }

    @PostMapping("/inicio_sesion")
    public String inicio_sesion (
        @RequestParam String Usuario,
        @RequestParam String contraseña,
        Model model){

            String mensaje = "";
            boolean usuarioiniciado = false;

            if (!usuariosRegistrados.contains(Usuario)){
                mensaje = "Nombre de usuario no encontrado";
                
                model.addAttribute("mensaje", mensaje);
                model.addAttribute("usuarioiniciado", usuarioiniciado);
                model.addAttribute("mostrarResultado", true);
                return "inicio_sesion";
            
            }else {

                int posicion = usuariosRegistrados.indexOf(Usuario);
                String contraseña_correcta = contraseñasRegistradas.get(posicion);

                if (contraseña.equals(contraseña_correcta)){
                    return "redirect:/productos";
                }else{
                    mensaje = " contraseña incorrecta";
                    
                    model.addAttribute("mensaje", mensaje);
                    model.addAttribute("usuarioiniciado", usuarioiniciado);
                    model.addAttribute("mostrarResultado", true);
                    return "inicio_sesion";
                }
            }  
    }

    @GetMapping("/productos")
    public String verproductos(Model model){
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());
        model.addAttribute("totalCarrito", Carrito.size());
        model.addAttribute("carrito", Carrito);

        return "productos";
    }

    @PostMapping("/agregar_carrito")
    public String  agregar_carrito(@RequestParam String producto, Model model){

        String mensaje = "";
        boolean agregado = false;

        if (productos.contains(producto)){
            Carrito.add(producto);
            mensaje = "producto agregado";
            agregado = true;

        } else{
            mensaje = "el producto no existe";
            agregado = false;
        }

        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());
        model.addAttribute("totalCarrito", Carrito.size());
        model.addAttribute("carrito", Carrito);
        model.addAttribute("mensaje", mensaje);
        model.addAttribute("agregado", agregado);
        model.addAttribute("mostrarResultado", true);
        
        return "productos";
    }

    @GetMapping("/calculadora")
    public String mostrarCalculadora(){
        return "calculadora";
    }
    
    @GetMapping("/calcular")
    public String calcular(@RequestParam String n1,
                             @RequestParam String n2, 
                             @RequestParam String opcion,
                             Model model) {

        double numero1 = Double.parseDouble(n1);
        double numero2 = Double.parseDouble(n2);


        String mensaje = "";
        boolean calculo = false;
        
        while (true){

            if (opcion.equals("1") ){
                double resultado = numero1 + numero2;
                mensaje = "el resultado es " + resultado;
                calculo = true;
                break;

            }else if(opcion.equals("2")){
                double resultado = numero1 - numero2;
                mensaje = "el resultado es" + resultado;
                calculo = true;
                break;

            }else if(opcion.equals("3")){
                double resultado = numero1 * numero2;
                mensaje = "el resultado es" + resultado;
                calculo = true;
                break;
                
            }else if (opcion.equals("4")){
                if (numero2 != 0){
                    double resultado = numero1 / numero2;
                    mensaje = "El resultado es :"+ resultado;
                    calculo = true;
                    break;
                    
                }else{
                    mensaje = "Error no se puede dividir por 0";
                }
                

            }else{
                mensaje = "opcion invalida";
                break;
            }
        }
        model.addAttribute("mensaje", mensaje);
        model.addAttribute("calculo", calculo);
        model.addAttribute("mostrarResultado", true);

        return "calculadora";
    }

    @GetMapping("/eliminar_producto")
    public String eliminarProducto(@RequestParam String producto,Model model ){

        String mensaje = "";
        boolean eliminado = false;

        if(Carrito.contains(producto)){
            Carrito.remove(producto);
            mensaje = "producto eliminado";
            eliminado = true;

        }else{
            mensaje = "el producto no esta en el carrito";
            eliminado = false;
        }    

   model.addAttribute("productos", productos);
   model.addAttribute("totalProductos", productos.size());
   model.addAttribute("totalCarrito", Carrito.size());
   model.addAttribute("carrito", Carrito);
   model.addAttribute("mensaje", mensaje);
   model.addAttribute("eliminado", eliminado);
   model.addAttribute("mostrarResultado", true);
   
   return "productos";
}    
}