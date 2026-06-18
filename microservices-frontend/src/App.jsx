import {
    BrowserRouter,
    Routes,
    Route
} from "react-router-dom";

import Login from "./pages/Login";
import Products from "./pages/Products";
import MyOrders from "./pages/MyOrders";
import AdminRoute from "./routes/AdminRoute";
import AddProduct from "./pages/AddProduct";

import ProtectedRoute from "./routes/ProtectedRoute";
import AdminProducts from "./pages/AdminProducts.jsx";

function App() {

    return (
        <BrowserRouter>

            <Routes>

                {/* Public Route */}
                <Route path="/" element={<Login />} />

                {/* Protected Routes */}
                <Route
                    path="/products"
                    element={
                        <ProtectedRoute>
                            <Products />
                        </ProtectedRoute>
                    }
                />

                <Route
                    path="/my-orders"
                    element={
                        <ProtectedRoute>
                            <MyOrders />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/admin/add-product"
                    element={
                        <AdminRoute>
                            <AddProduct />
                        </AdminRoute>
                    }
                />
                <Route path="/admin/products" element={<AdminProducts />} />

            </Routes>

        </BrowserRouter>
    );
}

export default App;