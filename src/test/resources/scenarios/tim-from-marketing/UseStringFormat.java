public class Badge {

    public String print(Integer id, String name, String department) {
        String worksAt = department == null ? "OWNER" : department.toUpperCase();

        if (id == null) {
            return String.format("%s - %s", name, worksAt);
        }

        return String.format("[%d] - %s - %s", id, name, worksAt);
    }

}
