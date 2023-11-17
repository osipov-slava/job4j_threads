package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), account) != null;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        Optional<Account> fromOld = getById(fromId);
        Optional<Account> toOld = getById(fromId);
        if (fromOld.isPresent() && toOld.isPresent() && fromOld.get().amount() - amount >= 0) {
            Account fromNew = new Account(fromId, fromOld.get().amount() - amount);
            Account toNew = new Account(toId, toOld.get().amount() + amount);
            update(fromNew);
            update(toNew);
            return true;
        }
        return false;
    }
}
