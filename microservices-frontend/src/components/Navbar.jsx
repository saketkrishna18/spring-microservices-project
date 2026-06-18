import { Link, useNavigate } from "react-router-dom";

function Navbar() {
    const navigate = useNavigate();
    const role = localStorage.getItem("role");
    const logout = () => {
        localStorage.removeItem("token");
        navigate("/");
    };

    return (
        <div style={{ padding: "10px", background: "#333", color: "white" }}>

            <Link to="/products" style={{ margin: "10px", color: "white" }}>
                Products
            </Link>
            {role === "USER" && (
            <Link to="/my-orders" style={{ margin: "10px", color: "white" }}>
                My Orders
            </Link>
            )}

            {role === "ADMIN" && (
                <Link
                    to="/admin/products"
                    style={{ margin: "10px", color: "white" }}
                >
                    Admin Panel
                </Link>
            )}
            <button
                onClick={logout}
                style={{ marginLeft: "20px" }}
            >
                Logout
            </button>

        </div>
    );
}

export default Navbar;