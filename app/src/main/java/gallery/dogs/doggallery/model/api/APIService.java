package gallery.dogs.doggallery.model.api;


import gallery.dogs.doggallery.model.pojo.ResObj;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {

    @GET(Client.RANDOM)
    Observable<ResObj> getHomeImages();

    @GET("/api/breed/{breed}/images")
    Observable<ResObj> getDogBreed(@Path(value = "breed") String breed);

}