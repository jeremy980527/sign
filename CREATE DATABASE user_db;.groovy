CREATE DATABASE user_db;

USE user_db;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);



app.post('/login', (req, res) => {
    const { username, password } = req.body;

    if (!username || !password) {
        return res.status(400).json({ success: false, message: '用戶名和密碼不可為空' });
    }

    // 查找用戶
    db.query('SELECT * FROM users WHERE username = ?', [username], (err, results) => {
        if (err || results.length === 0) {
            return res.status(400).json({ success: false, message: '用戶不存在' });
        }

        const user = results[0];

        // 比較密碼
        if (bcrypt.compareSync(password, user.password)) {
            res.json({ success: true, message: '登入成功' });
        } else {
            res.status(400).json({ success: false, message: '密碼錯誤' });
        }
    });
});
