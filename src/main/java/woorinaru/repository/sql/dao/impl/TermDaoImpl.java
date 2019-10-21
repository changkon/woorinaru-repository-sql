package woorinaru.repository.sql.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import woorinaru.core.command.UpdateCommand;
import woorinaru.core.dao.spi.TermDao;
import woorinaru.core.model.management.administration.Term;
import woorinaru.repository.sql.adapter.TermAdapter;
import woorinaru.repository.sql.mapping.model.TermMapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static woorinaru.repository.sql.util.EntityManagerFactoryUtil.getEntityManager;

public class TermDaoImpl implements TermDao {

    private static final Logger LOGGER = LogManager.getLogger(TermDao.class);

    @Override
    public void create(Term term) {
        LOGGER.debug("Creating a term resource");
        // Map file
        TermMapper mapper = TermMapper.MAPPER;
        woorinaru.repository.sql.entity.management.administration.Term termEntity = mapper.mapToEntity(term);

        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(termEntity);
        em.getTransaction().commit();
        em.close();

        LOGGER.debug("Finished creating a term resource");
    }

    @Override
    public Optional<Term> get(int id) {
        LOGGER.debug("Retrieving a term with id: %d", id);
        EntityManager em = getEntityManager();
        woorinaru.repository.sql.entity.management.administration.Term termEntity = em.find(woorinaru.repository.sql.entity.management.administration.Term.class, id);

        LOGGER.debug("Term with id: %d. Found: %s", id, termEntity == null ? "True" : "False");

        TermMapper mapper = TermMapper.MAPPER;

        Optional<Term> termModel = Stream.ofNullable(termEntity)
            .map(mapper::mapToModel)
            .findFirst();

        em.close();

        return termModel;
    }

    @Override
    public void delete(Term term) {
        LOGGER.debug("Deleting term with id: %d", term.getId());

        // Map file
        EntityManager em = getEntityManager();
        woorinaru.repository.sql.entity.management.administration.Term deleteTermEntity = em.find(woorinaru.repository.sql.entity.management.administration.Term.class, term.getId());

        if (deleteTermEntity != null) {
            em.getTransaction().begin();
            em.remove(deleteTermEntity);
            em.getTransaction().commit();
            LOGGER.debug("Term deleted");
        } else {
            LOGGER.debug("Term with id: '%d' not found. Could not be deleted", term.getId());
        }

        em.close();
    }

    @Override
    public void modify(UpdateCommand<Term> updateCommand) {
        Term termModel = updateCommand.getReceiver();
        LOGGER.debug("Modifying term with id: %d", termModel.getId());
        EntityManager em = getEntityManager();
        woorinaru.repository.sql.entity.management.administration.Term existingTermEntity = em.find(woorinaru.repository.sql.entity.management.administration.Term.class, termModel.getId());

        if (existingTermEntity != null) {
            Term termAdapter = new TermAdapter(existingTermEntity, em);
            updateCommand.setReceiver(termAdapter);
            updateCommand.execute();
        } else {
            LOGGER.debug("Term with id: '%d' not found. Could not be modified", termModel.getId());
        }

        em.close();
    }

    @Override
    public List<Term> getAll() {
        LOGGER.debug("Retrieving all terms");
        TermMapper mapper = TermMapper.MAPPER;

        EntityManager em = getEntityManager();
        TypedQuery<woorinaru.repository.sql.entity.management.administration.Term> query = em.createQuery("SELECT t FROM Term t", woorinaru.repository.sql.entity.management.administration.Term.class);
        List<woorinaru.repository.sql.entity.management.administration.Term> termEntities = query.getResultList();

        List<Term> terms = termEntities.stream()
            .map(mapper::mapToModel)
            .collect(Collectors.toList());

        LOGGER.debug("Retrieved %d terms", terms.size());
        em.close();
        return terms;
    }
}
