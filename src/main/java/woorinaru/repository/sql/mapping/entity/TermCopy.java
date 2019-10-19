package woorinaru.repository.sql.mapping.entity;

import woorinaru.repository.sql.entity.management.administration.Term;

public class TermCopy implements Copy<Term> {
    @Override
    public void copy(Term src, Term dest) {
        dest.setId(src.getId());
        dest.setStartDate(src.getStartDate());
        dest.setEndDate(src.getEndDate());
        dest.setEvents(src.getEvents());
        dest.setTerm(src.getTerm());
        dest.setStaffMembers(src.getStaffMembers());
    }
}
