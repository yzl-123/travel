import com.it.travel.entity.User;
import com.it.travel.service.impl.FavoriteServiceImpl;

public class testFavorite {
    public void FavoriteServiceImpl(){
        FavoriteServiceImpl favoriteService = new FavoriteServiceImpl();
        User user = new User();
        user.setUid(2);

        favoriteService.addFavorite(1,user);

        System.out.println();
    }
}
