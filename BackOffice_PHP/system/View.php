<?php

namespace FSF;

class View
{
    /** @var  Session */
    protected $session;
    private $viewPath;
    private $params = array();

    /**
     * @param array $params
     */
    public function __construct($params = array())
    {
        $this
            ->addParams($params)
            ->setParam("css", array())
            ->setParam("js", array());
    }

    /**
     * @param string $param
     * @return mixed
     */
    public function __get($param)
    {
        return $this->getParam($param);
    }

    /**
     * @param string $param
     * @param mixed $value
     * @return View
     */
    public function __set($param, $value)
    {
        return $this->setParam($param, $param);
    }

    /**
     * @param array $params
     * @return View
     */
    public function addParams($params)
    {
        $this->params = array_merge($this->params, $params);

        return $this;
    }

    /**
     * @param string $name
     * @param mixed $value
     * @return View
     */
    public function setParam($name, $value)
    {
        $this->params[$name] = $value;

        return $this;
    }

    /**
     * @param string $name
     * @return mixed
     */
    public function getParam($name)
    {
        return isset($this->params[$name]) ? $this->params[$name] : null;
    }

    /**
     * @return array
     */
    public function getParams()
    {
        return $this->params;
    }

    /**
     * @return string
     */
    public function render()
    {
        if (is_null($this->getViewPath())) {
            throw new \InvalidArgumentException('View must be defined');
            echo 'View must be defined';
        }
        if (!file_exists($this->getViewPath())) {
            throw new \OutOfBoundsException("File does not exist : ".$this->getViewPath());
            echo "File does not exist : ".$this->getViewPath();
        }
        //Output Buffering
        ob_start();
        require($this->getViewPath());

        return ob_get_clean();
    }

    /**
     * Define view to use
     * @param string $viewPath Full path to view
     * @return View
     */
    public function setViewPath($viewPath)
    {
        $this->viewPath = (string)$viewPath;

        return $this;
    }

    /**
     * @return mixed
     */
    public function getViewPath()
    {
        return $this->viewPath;
    }

    /**
     * @return Session
     */
    public function getSession()
    {
        if (is_null($this->session)) {
            $this->session = Session::getInstance();
        }

        return $this->session;
    }

    /**
     * @param Session $session
     * @return Controller
     */
    public function setSession(Session $session)
    {
        $this->session = $session;

        return $this;
    }
}