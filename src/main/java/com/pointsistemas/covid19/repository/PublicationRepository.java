package com.pointsistemas.covid19.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pointsistemas.covid19.model.Publication;

public interface PublicationRepository extends JpaRepository<Publication, Long> {

	Optional<Publication> findByTitleAndNotice(String title, String notice);

}
