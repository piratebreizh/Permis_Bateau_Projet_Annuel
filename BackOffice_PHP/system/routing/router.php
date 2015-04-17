<?php

namespace FSF\Routing;

class Router {

    /** @var array List of routes */
    protected $routes = array();

    /** @var  Request */
    protected $originalRequest;

    function __construct()
    {
    }

    /**
     * @param Request $request
     */
    public function dispatch($request)
    {
        $this->originalRequest = $request;
        var_dump($request);
    }

}