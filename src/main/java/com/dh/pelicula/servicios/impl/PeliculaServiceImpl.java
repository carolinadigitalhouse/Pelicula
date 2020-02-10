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

	private boolean checkTituloAvailable(Pelicula peli){
		Pelicula peliFound = peliculaRepository.findTituloIs(peli.getTitulo()); // optional por el null
		if (peliFound!=null) {
			return false;
		}
		return true;
	}	
}
