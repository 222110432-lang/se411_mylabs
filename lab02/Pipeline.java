import java.util.ArrayList;
import java.util.List;

public class Pipeline<T, R> {

    private final List<Transformer<?, ?>> transformers;

    public Pipeline() {
        this.transformers = new ArrayList<>();
    }

    private Pipeline(List<Transformer<?, ?>> transformers) {
        this.transformers = transformers;
    }

    public <V> Pipeline<T, V> addTransformer(Transformer<R, V> transformer) {
        List<Transformer<?, ?>> newTransformers = new ArrayList<>(transformers);
        newTransformers.add(transformer);

        return new Pipeline<>(newTransformers);
    }

    @SuppressWarnings("unchecked")
    public R execute(T input) {
        Object current = input;

        for (Transformer<?, ?> transformer : transformers) {
            current = ((Transformer<Object, Object>) transformer).transform(current);
        }

        return (R) current;
    }
}