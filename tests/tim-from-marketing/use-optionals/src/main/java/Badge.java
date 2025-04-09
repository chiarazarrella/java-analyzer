public class Badge {

    public String print(Integer id, String name, String department) {
        Optional<Integer> idOpt = Optional.ofNullable(id);
        Optional<String> departmentOpt = Optional.ofNullable(department);

        String worksAt = departmentOpt
                .map(String::toUpperCase)
                .orElse("OWNER");

        return idOpt
                .map(val -> "[" + val + "] - " + name + " - " + worksAt)
                .orElse(name + " - " + worksAt);
    }
}
