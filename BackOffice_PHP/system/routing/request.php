<?php

namespace FSF\Routing;

/**
 * Class Request
 * Handle HTTP Request
 * @package FSF
 */
class Request
{
    const HTTP_X_REQUESTED_WITH = 'HTTP_X_REQUESTED_WITH';
    const HTTP_X_REQUESTED_WITH_AJAX = 'XMLHttpRequest';

    const REQUEST_URI = 'REQUEST_URI';
    const QUERY_STRING = 'QUERY_STRING';

    const REQUEST_METHOD = 'REQUEST_METHOD';
    const REQUEST_METHOD_GET = 'GET';
    const REQUEST_METHOD_POST = 'POST';

    /** @var array */
    protected $get = array();
    /** @var array */
    protected $post = array();
    /** @var array */
    protected $server = array();

    function __construct()
    {
        $this->get = $_GET;
        $this->post = $_POST;
        $this->server = $_SERVER;
    }

    /**
     * @param string $name
     * @param mixed $defaultValue
     * @return mixed
     */
    public function get($name, $defaultValue = null)
    {
        return ($this->getMethod() == self::REQUEST_METHOD_POST)
            ? $this->getPost($name, $defaultValue)
            : $this->getGet($name, $defaultValue);
    }

    /**
     * @param string $name
     * @param mixed $defaultValue
     * @return mixed
     */
    public function getGet($name, $defaultValue = null)
    {
        return $this->hasGet($name) ? $this->get[$name] : $defaultValue;
    }

    /**
     * @param string $name
     * @param mixed $defaultValue
     * @return mixed
     */
    public function getPost($name, $defaultValue = null)
    {
        return $this->hasPost($name) ? $this->post[$name] : $defaultValue;
    }

    /**
     * @param string $name
     * @param mixed $defaultValue
     * @return mixed
     */
    public function getServer($name, $defaultValue = null)
    {
        return $this->hasServer($name) ? $this->server[$name] : $defaultValue;
    }

    /**
     * Check if request is Ajax request
     * @return bool
     */
    public function isAjax()
    {
        $requestClient = $this->getServer(self::HTTP_X_REQUESTED_WITH);

        return (strtolower($requestClient) == strtolower(self::HTTP_X_REQUESTED_WITH_AJAX));
    }

    /**
     * @return string
     */
    public function getMethod()
    {
        return $this->getServer(self::REQUEST_METHOD);
    }

    /**
     * @param bool $withQueryString
     * @return string
     */
    public function getRequestUri($withQueryString = false)
    {
        $requestUri = $this->getServer(self::REQUEST_URI);

        return $withQueryString ?
            $requestUri
            : str_replace('?' . $this->getServer(self::QUERY_STRING), '', $requestUri);
    }


    /**
     * @param string $name
     * @return boolean
     */
    public function has($name)
    {
        return ($this->getMethod() == self::REQUEST_METHOD_POST)
            ? $this->hasPost($name)
            : $this->hasGet($name);
    }

    /**
     * @param string $name
     * @return bool
     */
    public function hasPost($name)
    {
        return array_key_exists($name, $this->post);
    }

    /**
     * @param string $name
     * @return bool
     */
    public function hasGet($name)
    {
        return array_key_exists($name, $this->get);
    }

    /**
     * @param string $name
     * @return bool
     */
    public function hasServer($name)
    {
        return array_key_exists($name, $this->server);
    }
}