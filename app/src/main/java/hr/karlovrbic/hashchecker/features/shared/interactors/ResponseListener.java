package hr.karlovrbic.hashchecker.features.shared.interactors;

/**
 * Response listener used for all interactor responses.
 *
 * @author Karlo Vrbic
 * @version 1.0, 28.12.2016.
 */
public interface ResponseListener<T> {

    void onSuccess(T result);

    void onError(int code, String message);
}