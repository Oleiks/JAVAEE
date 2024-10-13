package com.example.demo.author;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class AuthorRepository {

    private final Set<Author> authors = new HashSet<>();

    public List<Author> getAuthors() {
        return new ArrayList<>(authors);
    }

    public void saveAuthors(Author author) {
        authors.add(author);
    }

    public Optional<Author> getAuthorByUUID(UUID uuid) {
        return getAuthors().stream().filter(author -> author.getId().equals(uuid)).findFirst();
    }

//    @SneakyThrows
//    @SuppressWarnings("unchecked")
//    private <T extends Serializable> T cloneAuthor(T object) {
//        try (ByteArrayInputStream is = new ByteArrayInputStream(writeObject(object).toByteArray());
//             ObjectInputStream ois = new ObjectInputStream(is)) {
//            return (T) ois.readObject();
//        }
//    }
//
//    private <T extends Serializable> ByteArrayOutputStream writeObject(T object) throws IOException {
//        try (ByteArrayOutputStream os = new ByteArrayOutputStream();
//             ObjectOutputStream oos = new ObjectOutputStream(os)) {
//            oos.writeObject(object);
//            return os;
//        }
//    }

}
