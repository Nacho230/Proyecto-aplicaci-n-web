package com.soquetes.Loquetes.servicios;

import com.soquetes.Loquetes.entidades.Usuario;
import com.soquetes.Loquetes.enumeraciones.Rol;
import com.soquetes.Loquetes.repositorios.Usuario_repositorio;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class Usuario_servicio implements UserDetailsService {

    private final Usuario_repositorio usuarioRepositorio;
    private final BCryptPasswordEncoder passwordEncoder;

    public Usuario_servicio(Usuario_repositorio usuarioRepositorio){
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public void registrar(Long id, String nombre, String apellido, String email,
                          String password, String password2){

        if (!password.equals(password2)){
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }

        if (usuarioRepositorio.buscar_por_email(email) != null){
            throw new IllegalArgumentException("El correo electronico ingresado ya está registrado");
        }

        Usuario usuario = usuarioRepositorio.getOne(id);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setRol(Rol.USUARIO);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuarioRepositorio.save(usuario);
    }


    @Transactional
    public void actualizar_usuario(Long id, String nombre, String apellido, String email,
                                   String password, String password2){

        if (!password.equals(password2)){
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();

            if (!usuario.getEmail().equals(email) && usuarioRepositorio.buscar_por_email(email) != null) {
                throw new IllegalArgumentException("El correo electrónico ya está registrado");
            }


            usuario.setEmail(email);
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setRol(Rol.USUARIO);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));

            usuarioRepositorio.save(usuario);
        }else{
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepositorio.buscar_por_email(email);

        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString()); // ROLE_

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuario_session", usuario);

            return new User(usuario.getEmail(), usuario.getPassword(), permisos);

        }else{
            throw new UsernameNotFoundException("Usuario no encontrado");
        }


        }
    //TODO: Sugerencia: Agregar una validación al registrar el email y quizas agregar una funcion para recuperar la contraseña
    }


























