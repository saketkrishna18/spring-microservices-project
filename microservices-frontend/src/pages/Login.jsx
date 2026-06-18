import { useState } from "react";
import api from "../api/axios";
import { useNavigate } from "react-router-dom";

function Login() {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const navigate = useNavigate();

    const handleLogin = async () => {
        try {
            const res = await api.post("/auth/login", {
                email,
                password
            });

            const token = res.data;

            localStorage.setItem("token", token);

// decode JWT
            const payload = JSON.parse(atob(token.split(".")[1]));

            console.log("ROLE:", payload.role);

            localStorage.setItem("role", payload.role);
            navigate("/products");

        } catch (error) {
            console.log(error);
            alert("Invalid credentials");
        }
    };

    return (
        <div>
            <h2>Login</h2>

            <input
                placeholder="Email"
                onChange={(e) => setEmail(e.target.value)}
            />

            <input
                type="password"
                placeholder="Password"
                onChange={(e) => setPassword(e.target.value)}
            />

            <button onClick={handleLogin}>
                Login
            </button>
        </div>
    );
}

export default Login;