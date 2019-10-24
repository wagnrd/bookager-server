package wagnrd.bookagerserver.bookshelf;

public class RelationNotFoundException extends RuntimeException {
    public RelationNotFoundException() {
        super("Relation not found!");
    }

    public RelationNotFoundException(Class entity1, Class entity2) {
        super("Relation between + \"" + entity1.getSimpleName() + "\" and \"" + entity2.getSimpleName() + "\"not found!");
    }
}
