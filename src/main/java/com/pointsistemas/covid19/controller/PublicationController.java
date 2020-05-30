package com.pointsistemas.covid19.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.pointsistemas.covid19.model.Publication;
import com.pointsistemas.covid19.repository.PublicationRepository;

@CrossOrigin
@RestController
@RequestMapping("/publications")
public class PublicationController {

	@Autowired
	private PublicationRepository publications;

	@GetMapping
	public List<Publication> list() {
		return publications.findAll();
	}

	@GetMapping("{id}")
	public ResponseEntity<Publication> searchById(@PathVariable Long id) {
		Optional<Publication> publication = publications.findById(id);
		if (publication.isPresent()) {
			return ResponseEntity.ok(publication.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Publication insert(@Valid @RequestBody Publication publication) {
		Optional<Publication> publicationExists = publications.findByTitleAndNotice(publication.getTitle(),
				publication.getNotice());
		if(publicationExists.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Já existe uma notícia com esse título.");
		}
		return publications.save(publication);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Publication> update(@PathVariable(value = "id") Long id, @Valid @RequestBody Publication publication){
		Optional<Publication> publicationExists = publications.findById(id);
		if(publicationExists.isPresent()) {
			publication.setId(id);
			publication = publications.save(publication);
			return ResponseEntity.ok(publication);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		publications.deleteById(id);
	}

}
