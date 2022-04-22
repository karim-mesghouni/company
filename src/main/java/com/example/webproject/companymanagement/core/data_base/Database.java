package com.example.webproject.companymanagement.core.data_base;

import com.example.webproject.companymanagement.Application;
import com.example.webproject.companymanagement.core.domain.conserns.HasId;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.AccessDeniedException;
import org.intellij.lang.annotations.Language;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.persistence.metamodel.EntityType;
import java.io.Serializable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.example.webproject.companymanagement.core.tools.Functions.safeSupplied;
import static com.example.webproject.companymanagement.core.tools.Functions.throwIfEmpty;

import static java.text.MessageFormat.format;

//
//@Component
//public class Database {
//    static ConfigurableApplicationContext context;
//
//    private Database(ConfigurableApplicationContext __cnx) {
//        context = __cnx;
//    }
//
//    @org.jetbrains.annotations.NotNull
//    public static EntityManager em() {
//        return context.getBean("localEntityManager", EntityManager.class);
//    }
//
//
//
//    public static void startTransaction() {
//        em().getTransaction().begin();
//    }
//
//    /**
//     * Run something inside a transaction
//     */
//    public static void inTransaction(Runnable action) {
//        startTransaction();
//        action.run();
//        commit();
//    }
//
//    /**
//     * Run something inside a transaction and return value of supplier
//     */
//    public static <T> T inTransaction(@org.jetbrains.annotations.NotNull Supplier<T> action) {
//        startTransaction();
//        var result = action.get();
//        commit();
//        return result;
//    }
//
//
//    @org.jetbrains.annotations.NotNull
//    public static <T> T getSingle(@org.jetbrains.annotations.NotNull @Language("HQL") String query, @org.jetbrains.annotations.NotNull Class<T> clazz) throws NoResultException {
//        return em().createQuery(query, clazz).setMaxResults(1).getSingleResult();
//    }
//
//    @org.jetbrains.annotations.NotNull
//    public static <T> TypedQuery<T> query(@Language("HQL") String query, Class<T> clazz) {
//        return em().createQuery(query, clazz);
//    }
//
//
//    @org.jetbrains.annotations.NotNull
//    public static <T> TypedQuery<T> querySingle(@Language("HQL") String query, Class<T> clazz) {
//        return em().createQuery(query, clazz).setMaxResults(1);
//    }
//
//
//    @org.jetbrains.annotations.NotNull
//    public static <T> javax.persistence.criteria.Predicate predicate(Class<T> clazz, QueryPredicate<T> predicate) {
//        var cb = em().getCriteriaBuilder();
//        var cq = cb.createQuery(clazz);
//        var r = cq.from(clazz);
//        return predicate.predicate(cb, r);
//    }
//
//    @org.jetbrains.annotations.NotNull
//    private static <T> TypedQuery<T> criteria(@org.jetbrains.annotations.NotNull Class<T> clazz, @org.jetbrains.annotations.NotNull QueryFilter<T> filter) {
//        var cb = em().getCriteriaBuilder();
//        var cq = cb.createQuery(clazz);
//        var r = cq.from(clazz);
//        filter.filtering(cq, cb, r);
//        return em().createQuery(cq);
//    }
//
//    public static <T> int count(@org.jetbrains.annotations.NotNull Class<T> clazz, @org.jetbrains.annotations.NotNull QueryFilter<T> filter) {
//        return criteria(clazz, filter).getResultList().size();
//    }
//
//    @org.jetbrains.annotations.NotNull
//    public static <T> List<T> findAll(@org.jetbrains.annotations.NotNull Class<T> clazz, int maxResults, @org.jetbrains.annotations.NotNull QueryFilter<T> filter) {
//        return criteria(clazz, filter).setMaxResults(maxResults).getResultList();
//    }
//
//    @org.jetbrains.annotations.NotNull
//    public static <T> List<T> findAll(@org.jetbrains.annotations.NotNull Class<T> clazz, @org.jetbrains.annotations.NotNull QueryFilter<T> filter) {
//        return criteria(clazz, filter).getResultList();
//    }
//
//    @org.jetbrains.annotations.NotNull
//    public static <T> T findOne(@org.jetbrains.annotations.NotNull Class<T> clazz, @org.jetbrains.annotations.NotNull QueryFilter<T> filter) throws NoResultException {
//        return criteria(clazz, filter).setMaxResults(1).getSingleResult();
//    }
//
//    public static <T> T findOrDefault(@org.jetbrains.annotations.NotNull Class<T> clazz, @org.jetbrains.annotations.Nullable Serializable id, Supplier<T> _default) {
//        return safeSupplied(() -> findOrThrow(clazz, id), _default);
//    }
//
//    @org.jetbrains.annotations.Nullable
//    public static EntityManager unsafeEntityManager() {
//        if (context() == null) return null;
//        return Application.getBean(EntityManager.class);
//    }
//
//    @org.jetbrains.annotations.Nullable
//    @Contract(value = "_, null -> null")
//    public static <T> T findOrNull(@org.jetbrains.annotations.NotNull Class<T> clazz, @org.jetbrains.annotations.Nullable Serializable id) {
//        return em().find(clazz, id);
//    }
//
//    @org.jetbrains.annotations.Nullable
//    @Contract(value = "_, null -> null")
//    public static <T> T findOrNull(@org.jetbrains.annotations.NotNull Class<T> clazz, @org.jetbrains.annotations.Nullable HasId id) {
//        return findOrNull(clazz, id != null ? id.getId() : null);
//    }
//
//    @org.jetbrains.annotations.Nullable
//    public static <T extends HasId> T findOrNull(@org.jetbrains.annotations.Nullable T input) {
//        if (input == null) return null;
//        //noinspection unchecked
//        return findOrNull((Class<T>) input.getClass(), input.getId());
//    }
//
//
//    @org.jetbrains.annotations.NotNull
//    public static <T> T findOrThrow(@org.jetbrains.annotations.NotNull Class<T> clazz, @org.jetbrains.annotations.Nullable Serializable id) throws EntityNotFoundException {
//        return throwIfEmpty(em().find(clazz, id), () -> new EntityNotFoundException(format("No entity of type {} with id {} was found", clazz, id)));
//    }
//
////    @org.jetbrains.annotations.NotNull
////    public static <T> T findOrThrow(@org.jetbrains.annotations.NotNull Class<T> clazz, @org.jetbrains.annotations.Nullable Serializable id, @org.jetbrains.annotations.NotNull String action) throws EntityNotFoundException {
////        return findOrThrow(clazz, id, (Consumer<T>) entity -> check(action, entity));
////    }
//
//    @org.jetbrains.annotations.NotNull
//    public static <T> T findOrThrow(@org.jetbrains.annotations.NotNull Class<T> clazz, @org.jetbrains.annotations.Nullable HasId id) throws EntityNotFoundException {
//        return throwIfEmpty(em().find(clazz, id == null ? null : id.getId()), () -> new EntityNotFoundException(format("No entity of type {} with id {} was found", clazz, id == null ? null : id.getId())));
//    }
//
//    @org.jetbrains.annotations.NotNull
//    public static <T extends HasId> T findOrThrow(@org.jetbrains.annotations.Nullable T input) throws EntityNotFoundException {
//        if (input == null) throw new EntityNotFoundException("could not find null entity");
//        //noinspection unchecked
//        return findOrThrow((Class<T>) input.getClass(), input.getId());
//    }
//
//    @org.jetbrains.annotations.NotNull
//    public static <T, R> R findOrThrow(@org.jetbrains.annotations.NotNull Class<T> clazz, @org.jetbrains.annotations.Nullable Serializable id, @org.jetbrains.annotations.NotNull Function<T, R> action) throws EntityNotFoundException, NullPointerException {
//        return throwIfEmpty(action.apply(findOrThrow(clazz, id)), NullPointerException::new);
//    }
//
//    @org.jetbrains.annotations.NotNull
//    public static <T, R> R findOrThrow(@org.jetbrains.annotations.NotNull Class<T> clazz, @org.jetbrains.annotations.Nullable HasId id, @org.jetbrains.annotations.NotNull Function<T, R> action) throws EntityNotFoundException {
//        return action.apply(findOrThrow(clazz, id));
//    }
//
////    @org.jetbrains.annotations.NotNull
////    public static <T, R> R findOrThrow(@org.jetbrains.annotations.NotNull Class<T> clazz, @org.jetbrains.annotations.Nullable Serializable id, @org.jetbrains.annotations.NotNull String permission, @org.jetbrains.annotations.NotNull Function<T, R> action) throws EntityNotFoundException, AccessDeniedException {
////        T entity = findOrThrow(clazz, id);
////        check(permission, entity);
////        return action.apply(entity);
////    }
//
////    @org.jetbrains.annotations.NotNull
////    public static <T, R> R findOrThrow(@org.jetbrains.annotations.NotNull Class<T> clazz, @org.jetbrains.annotations.Nullable HasId id, @org.jetbrains.annotations.NotNull String permission, @org.jetbrains.annotations.NotNull Function<T, R> action) throws EntityNotFoundException, AccessDeniedException {
////        T entity = findOrThrow(clazz, id);
////        check(permission, entity);
////        return action.apply(entity);
////    }
//
////    @org.jetbrains.annotations.NotNull
////    public static <T extends HasId, R> R findOrThrow(@org.jetbrains.annotations.Nullable T entity, @org.jetbrains.annotations.NotNull String permission, @org.jetbrains.annotations.NotNull Function<T, R> action) throws EntityNotFoundException, AccessDeniedException {
////        if (entity == null) throw new EntityNotFoundException("could not find null entity");
////        //noinspection unchecked
////        return findOrThrow((Class<T>) entity.getClass(), entity, permission, action);
////    }
//
//    @org.jetbrains.annotations.NotNull
//    public static <T extends HasId, R> R findOrThrow(@org.jetbrains.annotations.Nullable T entity, @org.jetbrains.annotations.NotNull Function<T, R> action) throws EntityNotFoundException, AccessDeniedException {
//        if (entity == null) throw new EntityNotFoundException("could not find null entity");
//        //noinspection unchecked
//        return findOrThrow((Class<T>) entity.getClass(), entity, action);
//    }
//
//    @org.jetbrains.annotations.NotNull
//    public static <T> T findOrThrow(@org.jetbrains.annotations.NotNull Class<T> clazz, @Nullable Serializable id, @org.jetbrains.annotations.NotNull Consumer<T> action) throws EntityNotFoundException {
//        T entity = findOrThrow(clazz, id);
//        action.accept(entity);
//        return entity;
//    }
//
//    @org.jetbrains.annotations.NotNull
//    public static <T> List<T> findAll(@org.jetbrains.annotations.NotNull Class<T> clazz) {
//        return em().createQuery("SELECT e FROM " + getEntityType(clazz).getName() + " e", clazz).getResultList();
//    }
//
//    @org.jetbrains.annotations.NotNull
//    public static <T> List<T> findAll(@org.jetbrains.annotations.NotNull Class<T> clazz, @org.jetbrains.annotations.NotNull java.util.function.Predicate<T> filter) {
//        return em().createQuery("SELECT e FROM " + getEntityType(clazz).getName() + " e", clazz).getResultStream().filter(filter).collect(Collectors.toList());
//    }
//
//    public static void remove(@org.jetbrains.annotations.NotNull Object entity) throws EntityNotFoundException, IllegalArgumentException {
//        em().remove(entity);
//    }
//
//    public static <T> Boolean remove(@org.jetbrains.annotations.NotNull T entity, @org.jetbrains.annotations.NotNull Consumer<T> consumer) throws IllegalArgumentException {
//        consumer.accept(entity);
//        em().remove(entity);
//        return true;
//    }
//
//    public static <T> Boolean remove(@org.jetbrains.annotations.NotNull Class<T> clazz, @org.jetbrains.annotations.NotNull Serializable id, @org.jetbrains.annotations.NotNull Consumer<T> consumer) throws EntityNotFoundException {
//        T entity = findOrThrow(clazz, id);
//        consumer.accept(entity);
//        remove(entity);
//        return true;
//    }
//
//    public static <T> Boolean afterRemoving(@org.jetbrains.annotations.NotNull Class<T> clazz, @org.jetbrains.annotations.NotNull Serializable id, @org.jetbrains.annotations.NotNull String action, @org.jetbrains.annotations.NotNull Consumer<T> consumer) throws EntityNotFoundException {
//        var entity = findOrThrow(clazz, id, action);
//        Database.startTransaction();
//        remove(entity);
//        Database.commit();
//        consumer.accept(entity);
//        return true;
//    }
//
//    public static <T> Boolean afterRemoving(@org.jetbrains.annotations.NotNull Class<T> clazz, @org.jetbrains.annotations.NotNull Serializable id, @org.jetbrains.annotations.NotNull Consumer<T> consumer) throws EntityNotFoundException {
//        var entity = findOrThrow(clazz, id);
//        Database.startTransaction();
//        remove(entity);
//        Database.commit();
//        consumer.accept(entity);
//        return true;
//    }
//
//    public static <T> Boolean remove(@org.jetbrains.annotations.NotNull Class<T> clazz, @org.jetbrains.annotations.NotNull Serializable id) throws EntityNotFoundException {
//        remove(findOrThrow(clazz, id));
//        return true;
//    }
//
//    public static <T> T removeNow(@org.jetbrains.annotations.NotNull Class<T> clazz, @org.jetbrains.annotations.NotNull Serializable id) throws EntityNotFoundException {
//        var e = findOrThrow(clazz, id);
//        Database.inTransaction(() -> em().remove(e));
//        return e;
//    }
//
//    @SuppressWarnings("UnusedReturnValue")
//    public static <T extends HasId> Boolean removeNow(T entity) throws EntityNotFoundException {
//        Database.inTransaction(() -> em().remove(entity));
//        return true;
//    }
//
////    public static <T> Boolean remove(@org.jetbrains.annotations.NotNull Class<T> clazz, @org.jetbrains.annotations.NotNull Serializable id, @org.jetbrains.annotations.NotNull String permission) throws EntityNotFoundException {
////        T entity = findOrThrow(clazz, id);
////        check(permission, entity);
////        remove(entity);
////        return true;
////    }
//
//    /**
//     * You must call {@link #startTransaction()} first
//     */
//    public static <T> T persist(@NotNull T entity) {
//        em().persist(entity);
//        return entity;
//    }
//
//    /**
//     * flush the {@link #em() entity manager} and then commit any active transactions.
//     */
//    public static void commit() {
//        em().flush();
//        if (em().getTransaction().isActive()) {
//            em().getTransaction().commit();
//        }
//    }
//
//    /**
//     * rollback any active transactions.
//     */
//    public static void rollback() {
//        if (em().getTransaction().isActive()) {
//            em().getTransaction().rollback();
//        }
//    }
//
//    public static EntityType<?> getEntityType(String name) {
//        return em()
//                .getMetamodel()
//                .getEntities()
//                .stream()
//                .filter(t -> t.getName().equals(name))
//                .findFirst()
//                .orElseThrow();
//    }
//
//    public static <T> EntityType<?> getEntityType(Class<T> clazz) {
//        return em()
//                .getMetamodel()
//                .getEntities()
//                .stream()
//                .filter(t -> t.getJavaType().getName().equals(clazz.getName()))
//                .findFirst()
//                .orElseThrow();
//    }
//
//    @Bean(name = "localEntityManager")
//    @Scope("request")
//    public Object localEntityManager(EntityManagerFactory factory) {
//        return factory.createEntityManager();// will be called once for each request
//    }
//}
