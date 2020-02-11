package com.dh.pelicula.servicios.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dh.pelicula.entidades.Pelicula;
import com.dh.pelicula.repositorios.PeliculaRepository;
import com.dh.pelicula.servicios.PeliculaService;

@Service
public class PeliculaServiceImpl implements PeliculaService{

	@Autowired 
	private PeliculaRepository peliculaRepository;

	
	@Override
	public boolean agregar(Pelicula peli){
		if (checkTituloAvailable(peli)) {
			peli.setRandom_num(Math.round(Math.random() * 99999999));
			peliculaRepository.save(peli);
		}
		return true;
		
	}
	@Override
	public boolean editarPelicula(Pelicula movie) {
		//puedo editar una pelicula asi sin validar?
		peliculaRepository.save(movie);
		return false;
	}

	@Override
	public Pelicula buscarPelicula(Long id)  throws Exception{
		// TODO Auto-generated method stub
		Optional<Pelicula> pelicula= peliculaRepository.findById(id);
		if (pelicula.isPresent()) {
			throw new Exception("pelicula no disponible");
		}

		return pelicula.get();
	}
	
	@Override
	public Pelicula buscarPeliculaPorTitulo(String titulo) {
		Pelicula peliFound = peliculaRepository.findTituloIs(titulo);
		return peliFound;
	}
	
	@Override
	public void eliminarPelicula(long id) {
		//Voy a la BD a buscar la pelicula por id para borrarla
		//Pero me puede pasar que esa pelicula no exista en la BD por eso
		//puedo usar el metodo orElseThrow() para lanzar una exception. 
		//En caso de que tengamos la exception debemos manejarla... se los dejo para que investiguen cómo hacerlo
		//Despues les doy cómo vamos a manejar esa exception pero estaría ideal si investigan por su cuenta que podemos hacer en este caso
		Pelicula peliABorrar = peliculaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalido Id:" + id));
		peliculaRepository.delete(peliABorrar);
       
	}

	private boolean checkTituloAvailable(Pelicula peli){
		Pelicula peliFound = peliculaRepository.findTituloIs(peli.getTitulo()); 
		if (peliFound!=null) {
			return false;
		}
		return true;
	}
		
}
