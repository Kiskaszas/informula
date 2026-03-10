package hu.informula.movieinfo.repository;

import hu.informula.movieinfo.entity.SearchedElement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchedElementRepository extends JpaRepository<SearchedElement, Long> {}