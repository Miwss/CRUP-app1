package ua.anistrai.crud.dao;

import org.springframework.stereotype.Component;
import ua.anistrai.crud.models.Person;

import java.util.ArrayList;
import java.util.List;
//данный класс общается со списком, будет извлекать людей со списка, находить какого то человека по айди, добавлять, обновлять, удалять человека из списка.
@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    private List<Person> people;

    {
        people = new ArrayList<>();

        people.add(new Person(++PEOPLE_COUNT, "Tom", 24, "tom@mail.ru"));
        people.add(new Person(++PEOPLE_COUNT, "Bob", 52, "bob@mail.ru"));
        people.add(new Person(++PEOPLE_COUNT, "Mike", 18, "mike@yahoo.com"));
        people.add(new Person(++PEOPLE_COUNT, "Katy", 34, "katy@gmail.com"));
    }

    public List<Person> index(){
        return people;
    }

    public Person show(int id){

        return people.stream().filter(person-> person.getId() == id).findAny().orElse(null);// Используем лямбда выражения для поиска человека по id
    }
    public void save(Person person){
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }
    public void update(int id, Person updatePerson){
        Person personToBeUpdated = show(id);

        personToBeUpdated.setName(updatePerson.getName());
        personToBeUpdated.setAge(updatePerson.getAge());
        personToBeUpdated.setEmail(updatePerson.getEmail());
    }
    public void delete(int id){
        people.removeIf(p -> p.getId() == id);
    }
}
