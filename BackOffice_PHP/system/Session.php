<?php

namespace FSF;

use FSF\Entity\User;

class Session
{
    const SESSION_STARTED = true;
    const SESSION_NOT_STARTED = false;

    /** @var  Session */
    private static $instance;
    /** @var  bool */
    private $sessionState;

    /**
     * Retrieve session system - start session if not started
     * @return Session
     */
    public static function getInstance()
    {
        if (!isset(self::$instance)) {
            self::$instance = new self;
        }

        self::$instance->start();

        return self::$instance;
    }

    /**
     * Start session if not started and return (bool) whether session is started
     * @return bool
     */
    private function start()
    {
        if (!$this->isStarted()) {
            $this->sessionState = session_start();
        }

        return $this->sessionState;
    }

    /**
     * Check whether session is started
     * @return bool
     */
    private function isStarted()
    {
        if (is_null($this->sessionState)) {
            $this->sessionState = (session_status() === PHP_SESSION_ACTIVE ? self::SESSION_STARTED : self::SESSION_NOT_STARTED);
        }

        return $this->sessionState == self::SESSION_STARTED;
    }

    /**
     * Check whether a specific information exists in session
     * @param string $name
     * @return bool
     */
    public function has($name)
    {
        return array_key_exists($name, $_SESSION);
    }

    /**
     * Retrieve a session value
     * @param string $name
     * @param mixed  $default
     * @return mixed
     */
    public function get($name, $default = null)
    {
        return $this->has($name) ? $_SESSION[$name] : $default;
    }

    /**
     * Define a specific value in session
     * @param string $name
     * @param mixed  $value
     * @return Session
     */
    public function set($name, $value)
    {
        $_SESSION[$name] = $value;

        return $this;
    }

    /**
     * Forget all values in session without destructing it
     * @return Session
     */
    public function clear()
    {
        $_SESSION = array();

        return $this;
    }

    /**
     * Delete a specific information from session
     * @param string $name
     * @return Session
     */
    public function remove($name)
    {
        if ($this->has($name)) {
            unset($_SESSION[$name]);
        }

        return $this;
    }

    /**
     * Destroy current session
     * @return bool
     */
    public function destroy()
    {
        if ($this->isStarted()) {
            $this->sessionState = !session_destroy();
            unset($_SESSION);

            return !$this->sessionState;
        }

        return self::SESSION_NOT_STARTED;
    }

    /**
     * @param User $user
     * @return Session
     */
    public function setUser(User $user)
    {
        $this->set("user", $user);

        return $this;
    }

    /**
     * @return User
     */
    public function getUser()
    {
        return $this->get("user");
    }
}