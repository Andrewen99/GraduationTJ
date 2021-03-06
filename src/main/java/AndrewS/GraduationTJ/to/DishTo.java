package AndrewS.GraduationTJ.to;


import java.time.LocalDate;
import java.util.Objects;

public class DishTo {
    private Integer id;
    private String name;
    private int price;

    private LocalDate date;

    public DishTo(Integer id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public DishTo() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name,id,price);
    }

    @Override
    public boolean equals(Object o) {
        if(this==o)return true;
        if(o==null || getClass()!=o.getClass())return false;
        DishTo d = (DishTo)o;
        return this.name.equals(d.name) && Objects.equals(id,d.id);
    }

    @Override
    public String toString() {
        return "DishTo{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
