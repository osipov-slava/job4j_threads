package ru.job4j.cache;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.id(), model) == null;
    }

    public boolean update(Base model) throws OptimisticException {
        Base result = memory.computeIfPresent(model.id(),
                (id, value) -> {
                    if (value.version() != model.version()) {
                        throw new OptimisticException("Versions are not equal");
                    }
                    return new Base(model.id(), model.name(), value.id() + 1);
                });
        return result != null;
    }

    public void delete(Base model) throws OptimisticException {
        memory.remove(model.id());
    }

    public Optional<Base> findById(int id) {
        return Optional.ofNullable(memory.get(id));
    }
}
