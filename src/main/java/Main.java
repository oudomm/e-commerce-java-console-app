import com.github.javafaker.Faker;
import controller.CartController;
import controller.OrderController;
import controller.ProductController;
import controller.UserController;
import lombok.RequiredArgsConstructor;
import model.dto.UserCreateDto;
import model.dto.UserResponseDto;
import model.dto.UserloginDto;
import model.entity.CartItem;
import model.entity.Product;
import model.handelException.UserAlreadyExistsException;
import model.repository.ProductRepository;
import view.CartView;
import view.ProductView;
import view.UI;

import java.util.*;

public class Main {
    public static void main(String[] args) throws UserAlreadyExistsException {
        UI.ui();
    }
}