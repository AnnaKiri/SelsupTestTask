import java.util.List;
import java.util.Objects;

public class Document {
    private Description description;
    private String doc_id;
    private String doc_status;
    private String doc_type;
    private boolean importRequest;
    private String owner_inn;
    private String participant_inn;
    private String producer_inn;
    private String production_date;
    private String production_type;
    private List<Product> products;
    private String reg_date;
    private String reg_number;

    public Document() {
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    public String getDoc_status() {
        return doc_status;
    }

    public void setDoc_status(String doc_status) {
        this.doc_status = doc_status;
    }

    public String getDoc_type() {
        return doc_type;
    }

    public void setDoc_type(String doc_type) {
        this.doc_type = doc_type;
    }

    public boolean isImportRequest() {
        return importRequest;
    }

    public void setImportRequest(boolean importRequest) {
        this.importRequest = importRequest;
    }

    public String getOwner_inn() {
        return owner_inn;
    }

    public void setOwner_inn(String owner_inn) {
        this.owner_inn = owner_inn;
    }

    public String getParticipant_inn() {
        return participant_inn;
    }

    public void setParticipant_inn(String participant_inn) {
        this.participant_inn = participant_inn;
    }

    public String getProducer_inn() {
        return producer_inn;
    }

    public void setProducer_inn(String producer_inn) {
        this.producer_inn = producer_inn;
    }

    public String getProduction_date() {
        return production_date;
    }

    public void setProduction_date(String production_date) {
        this.production_date = production_date;
    }

    public String getProduction_type() {
        return production_type;
    }

    public void setProduction_type(String production_type) {
        this.production_type = production_type;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String getReg_number() {
        return reg_number;
    }

    public void setReg_number(String reg_number) {
        this.reg_number = reg_number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return importRequest == document.importRequest
                && Objects.equals(description, document.description)
                && Objects.equals(doc_id, document.doc_id)
                && Objects.equals(doc_status, document.doc_status)
                && Objects.equals(doc_type, document.doc_type)
                && Objects.equals(owner_inn, document.owner_inn)
                && Objects.equals(participant_inn, document.participant_inn)
                && Objects.equals(producer_inn, document.producer_inn)
                && Objects.equals(production_date, document.production_date)
                && Objects.equals(production_type, document.production_type)
                && Objects.equals(products, document.products)
                && Objects.equals(reg_date, document.reg_date)
                && Objects.equals(reg_number, document.reg_number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, doc_id, doc_status, doc_type,
                importRequest, owner_inn, participant_inn, producer_inn,
                production_date, production_type, products, reg_date, reg_number);
    }

    @Override
    public String toString() {
        return "Document{" +
                "description=" + description +
                ", doc_id='" + doc_id + '\'' +
                ", doc_status='" + doc_status + '\'' +
                ", doc_type='" + doc_type + '\'' +
                ", importRequest=" + importRequest +
                ", owner_inn='" + owner_inn + '\'' +
                ", participant_inn='" + participant_inn + '\'' +
                ", producer_inn='" + producer_inn + '\'' +
                ", production_date='" + production_date + '\'' +
                ", production_type='" + production_type + '\'' +
                ", products=" + products +
                ", reg_date='" + reg_date + '\'' +
                ", reg_number='" + reg_number + '\'' +
                '}';
    }
}
